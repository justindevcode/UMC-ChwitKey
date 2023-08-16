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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

@Component
public class ArticleAssembler {

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

  public String calUploadedAt(LocalDateTime uploadedAt) {
    LocalDateTime now = LocalDateTime.now();
    int i = now.toLocalDate().compareTo(uploadedAt.toLocalDate());
    if (i==0) {
      if(now.getMinute() == uploadedAt.getMinute()) return Duration.between(uploadedAt.toLocalTime(), now.toLocalTime()).getSeconds() + "초 전";

      if(now.getHour() == uploadedAt.getHour()) return Duration.between(uploadedAt.toLocalTime(), now.toLocalTime()).getSeconds()/60 + "분 전";

      else return Duration.between(uploadedAt.toLocalTime(), now.toLocalTime()).toHours() + "시간 전";

    } else if (i > 0) {
      Period period = Period.between(uploadedAt.toLocalDate(), now.toLocalDate());
      if (period.getYears() > 0) return period.getYears() + "년 전";
      else if(period.getMonths() > 0) return period.getMonths() + "달 전";
      else if(period.getDays() > 0) return period.getDays() + "일 전";
    }
    return "알 수 없음";
  }
}
