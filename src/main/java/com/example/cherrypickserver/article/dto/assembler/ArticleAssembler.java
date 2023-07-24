package com.example.cherrypickserver.article.dto.assembler;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ArticleAssembler {
  public Article toEntity(CreateArticleReq createArticleReq) throws ParseException {
    return Article.builder()
            .articleName(createArticleReq.getArticleName())
            .contents(createArticleReq.getContents())
            .publisher(createArticleReq.getPublisher())
            .reporter(createArticleReq.getReporter())
            .registeredAt(parseDate(createArticleReq.getRegisteredAt()))
            .build();
  }

  private Date parseDate(String registeredAt) throws ParseException{
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(registeredAt);
  }

  public int getSearchType(String cond, String jobKeyword) {
    if (StringUtils.hasText(cond) && !StringUtils.hasText(jobKeyword))
      return 1;
    else if (!StringUtils.hasText(cond) && StringUtils.hasText(jobKeyword))
      return 2;
    else if (StringUtils.hasText(cond) && StringUtils.hasText(jobKeyword))
      return 3;
    else
      return 0;
  }
}
