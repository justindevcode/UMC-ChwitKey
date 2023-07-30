package com.example.cherrypickserver.article.dto.assembler;

import com.example.cherrypickserver.article.domain.*;
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
            .contents(createArticleReq.getContent())
            .publisher(createArticleReq.getPublisher())
            .reporter(createArticleReq.getReporter())
            .industry(Industry.fromValue(createArticleReq.getIndustry()))
            .uploadedAt(parseDate(createArticleReq.getUploadedAt()))
            .build();
  }

  private Date parseDate(String registeredAt) throws ParseException{
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(registeredAt);
  }

  public Pageable setSortType(Pageable pageable, String sortType) {
    SortType type = SortType.getSortTypeByName(sortType);
    if(type == SortType.ASC) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("uploadedAt").ascending());
    else if(type == SortType.DESC) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("uploadedAt").descending());
    else if(type == SortType.LIKE) pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("likeCount").descending());
    else pageable = PageRequest.of(0, pageable.getPageSize());
    return pageable;
  }

  public ArticleAttention toEntityAttention(Member member, Article article, AttentionType attentionType) {
    return ArticleAttention.builder()
            .member(member)
            .article(article)
            .attentionType(attentionType)
            .build();
  }
}
