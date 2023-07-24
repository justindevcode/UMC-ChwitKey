package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticlePhoto;
import com.example.cherrypickserver.article.domain.ArticleRepository;
import com.example.cherrypickserver.article.dto.assembler.ArticleAssembler;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.article.dto.response.DetailArticleRes;
import com.example.cherrypickserver.article.dto.response.SearchArticleRes;
import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

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
  public DetailArticleRes detailArticle(Long articleIdx)
  {
    return DetailArticleRes.toDto(articleRepository.findByIdAndIsEnable(articleIdx, true).orElseThrow(ArticleNotFoundException::new));
  }

  @Override
  public Page<SearchArticleRes> searchArticle(String cond, String jobKeyword, Pageable pageable)
  {
    return null;
  }


}
