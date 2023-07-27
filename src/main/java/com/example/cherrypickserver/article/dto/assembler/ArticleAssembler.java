package com.example.cherrypickserver.article.dto.assembler;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleLike;
import com.example.cherrypickserver.article.domain.AttentionType;
import com.example.cherrypickserver.article.dto.request.CreateArticleReq;
import com.example.cherrypickserver.member.domain.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ArticleAssembler {
  public Article toEntity(CreateArticleReq createArticleReq) throws ParseException {
    return Article.builder()
            .title(createArticleReq.getTitle())
            .content(createArticleReq.getContent())
            .publisher(createArticleReq.getPublisher())
            .reporter(createArticleReq.getReporter())
            .registeredAt(parseDate(createArticleReq.getRegisteredAt()))
            .build();
  }

  private Date parseDate(String registeredAt) throws ParseException{
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(registeredAt);
  }

  public Pageable setSortType(Pageable pageable, String sortType) {
    if(sortType.equals("ASC")) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("registeredAt").ascending());
    else if(sortType.equals("DESC")) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("registeredAt").descending());
    else if(sortType.equals("LIKE")) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("likeCount").descending());
    else pageable = PageRequest.of(0, pageable.getPageSize());
    return pageable;
  }

  public ArticleLike toEntityAttention(Member member, Article article, AttentionType attentionType) {
    return ArticleLike.builder()
            .member(member)
            .article(article)
            .attentionType(attentionType)
            .build();
  }
}
