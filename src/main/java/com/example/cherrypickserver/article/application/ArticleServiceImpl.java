package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.*;
import com.example.cherrypickserver.article.dto.assembler.ArticleAssembler;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.ScrapArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.article.exception.AlreadyAttendArticleException;
import com.example.cherrypickserver.article.exception.ArticleAttentionNotFoundException;
import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

  private final ArticleAttentionRepository articleAttentionRepository;
  private final MemberRepository memberRepository;
  private final ArticleRepository articleRepository;
  private final ArticlePhotoRepository articlePhotoRepository;
  private final ArticleAssembler articleAssembler;

  @Override
  public DetailArticleRes detailArticle(Long memberId, Long articleId) {
    Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
    Article article = articleRepository.findByIdAndIsEnable(articleId, true).orElseThrow(ArticleNotFoundException::new);
    List<ArticleAttention> attentionCheck = articleAttentionRepository.findByArticleAndMemberAndIsEnable(article, member, true);
    return DetailArticleRes.toDto(article, attentionCheck, articleAssembler.calUploadedAt(article.getUploadedAt()));
  }

  @Override
  public Page<SearchArticleRes> searchArticle(String cond, String sortType, Pageable pageable) {
    pageable = articleAssembler.setSortType(pageable, sortType);

    if (StringUtils.hasText(cond)) {
      Page<Article> articles = articleRepository.findByTitleContainingOrContentsContainingAndIsEnable(cond, cond, true, pageable);
      return articles.map(m -> SearchArticleRes.toDto(m, articleAssembler.calUploadedAt(m.getUploadedAt())));
    }
    Page<Article> articles = articleRepository.findByIsEnable(true, pageable);
    return articles.map(m -> SearchArticleRes.toDto(m, articleAssembler.calUploadedAt(m.getUploadedAt())));
  }

  @Override
  @Transactional
  public void attendArticle(Long articleId, Long memberId, String type) {
    Article article = articleRepository.findByIdAndIsEnable(articleId, true).orElseThrow(ArticleNotFoundException::new);
    Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
    AttentionType attentionType = AttentionType.getAttentionTypeByName(type);

    boolean present = articleAttentionRepository.findByArticleIdAndMemberIdAndAttentionTypeAndIsEnable(articleId, memberId, attentionType, true).isPresent();
    if (present) throw new AlreadyAttendArticleException();

    articleAttentionRepository.save(articleAssembler.toEntityAttention(member, article, attentionType));

    if (attentionType == AttentionType.LIKE) article.likeArticle();
  }

  @Override
  @Transactional
  public void unAttendArticle(Long articleId, Long memberId, String type) {
    ArticleAttention articleAttention = articleAttentionRepository.findByArticleIdAndMemberIdAndAttentionTypeAndIsEnable(articleId, memberId, AttentionType.getAttentionTypeByName(type), true).orElseThrow(ArticleAttentionNotFoundException::new);
    if(articleAttention.getAttentionType() == AttentionType.LIKE) articleAttention.getArticle().unLikeArticle();
    articleAttention.delete();
  }

  @Override
  public Page<SearchArticleRes> searchArticleByKeyword(Long memberId, String keyword, String sortType, Pageable pageable) {
    memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
    pageable = articleAssembler.setSortType(pageable, sortType);
    Page<Article> articles = articleRepository.findByTitleContainingOrContentsContainingAndIsEnable(keyword, keyword, true, pageable);
    return articles.map(m -> SearchArticleRes.toDto(m, articleAssembler.calUploadedAt(m.getUploadedAt())));
  }

  @Override
  public Page<SearchArticleRes> searchArticleByIndustry(Long memberId, String industry, String sortType, Pageable pageable) {
    memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
    pageable = articleAssembler.setSortType(pageable, sortType);
    Page<Article> articles = articleRepository.findByIndustryOrTitleContainingOrContentsContainingAndIsEnable(Industry.fromValue(industry), industry, industry, true, pageable);
    return articles.map(m -> SearchArticleRes.toDto(m, articleAssembler.calUploadedAt(m.getUploadedAt())));
  }

  @Override
  public List<ScrapArticleRes> getScrapArticle(Long memberId) {
    Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
    List<ArticleAttention> articleAttentions = articleAttentionRepository.findByMemberAndIsEnable(member, true);

    List<Article> scrappedArticles = new ArrayList<>();
    for (ArticleAttention articleAttention : articleAttentions) {
      articleRepository.findByIdAndIsEnable(articleAttention.getArticle().getId(), true).ifPresent(scrappedArticles::add);
    }
    return  scrappedArticles.stream().map(m -> ScrapArticleRes.toDto(m, articleAssembler.calUploadedAt(m.getUploadedAt()))).collect(Collectors.toList());
  }
}
