package com.example.cherrypickserver.article.dto.assembler;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ArticleAssembler {
  public Article toEntity(CreateArticleReq createArticleReq) {
    return Article.builder()
            .articleName(createArticleReq.getArticleName())
            .contents(createArticleReq.getContents())
            .publisher(createArticleReq.getPublisher())
            .articleImgKey(createArticleReq.getArticleImgUrl())
            .build();
  }

  public int getSearchType(String cond, String jobKeyword) {
    if(StringUtils.hasText(cond) && !StringUtils.hasText(jobKeyword))
      return 1;
    else if(!StringUtils.hasText(cond) && StringUtils.hasText(jobKeyword))
      return 2;
    else if(StringUtils.hasText(cond) && StringUtils.hasText(jobKeyword))
      return 3;
    else
      return 0;
  }
}
