package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.Article;
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


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

  private final ArticleRepository articleRepository;
  private final ArticleAssembler articleAssembler;
  @Override
  public Long createArticle(CreateArticleReq createArticleReq)
  {
    return articleRepository.save(articleAssembler.toEntity(createArticleReq)).getId();
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
