package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.*;
import com.example.cherrypickserver.article.dto.assembler.ArticleAssembler;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
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

import java.text.ParseException;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

  private final ArticleLikeRepository articleLikeRepository;
  private final MemberRepository memberRepository;
  private final ArticleRepository articleRepository;
  private final ArticlePhotoRepository articlePhotoRepository;
  private final ArticleAssembler articleAssembler;

  @Override
  public Long createArticle(CreateArticleReq createArticleReq) throws ParseException {
    Article article = articleRepository.save(articleAssembler.toEntity(createArticleReq));
    createArticleReq.getArticlePhotos().forEach(articlePhoto -> articlePhotoRepository.save(ArticlePhoto.builder().article(article).articleImgUrl(articlePhoto.getArticleImgUrl()).build()));
    return article.getId();
  }

  @Override
  public DetailArticleRes detailArticle(Long articleId) {
    return DetailArticleRes.toDto(articleRepository.findByIdAndIsEnable(articleId, true).orElseThrow(ArticleNotFoundException::new));
  }

  @Override
  public Page<SearchArticleRes> searchArticle(String cond, String sortType, Pageable pageable) {
    pageable = articleAssembler.setSortType(pageable, sortType);

    if (StringUtils.hasText(cond)) {
      Page<Article> articles = articleRepository.findByTitleContainingOrContentContainingAndIsEnable(cond, cond, true, pageable);
      return articles.map(SearchArticleRes::toDto);
    }
    Page<Article> articles = articleRepository.findByIsEnable(true, pageable);
    return articles.map(SearchArticleRes::toDto);
  }

  @Override
  @Transactional
  public void attendArticle(Long articleId, Long memberId, String type) {
    Article article = articleRepository.findByIdAndIsEnable(articleId, true).orElseThrow(ArticleNotFoundException::new);
    Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);

    AttentionType attentionType = AttentionType.getAttentionTypeByName(type);
    articleLikeRepository.save(articleAssembler.toEntityAttention(member, article, attentionType));

    if (attentionType == AttentionType.LIKE) article.likeArticle();
  }


}
