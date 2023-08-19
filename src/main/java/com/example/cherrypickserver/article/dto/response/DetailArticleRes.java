package com.example.cherrypickserver.article.dto.response;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleAttention;
import com.example.cherrypickserver.article.domain.AttentionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DetailArticleRes {
  private Long articleId;
  private String title;
  private String content;
  private String publisher;
  private String uploadedAt;
  private String reporter;
  private String industry;
  private String url;
  private Boolean isLiked;
  private Boolean isScrapped;
  private List<DetailArticlePhotoRes> articlePhoto;

  public static DetailArticleRes toDto(Article article, List<ArticleAttention> articleAttentions, String uploadedAt) {
    return DetailArticleRes.builder()
            .articleId(article.getId())
            .title(article.getTitle())
            .content(article.getContents())
            .publisher(article.getPublisher())
            .uploadedAt(uploadedAt)
            .reporter(article.getReporter())
            .industry(article.getIndustry().getValueGpt())
            .url(article.getUrl())
            .isLiked(checkAttention(articleAttentions, AttentionType.LIKE))
            .isScrapped(checkAttention(articleAttentions, AttentionType.SCRAP))
            .articlePhoto(article.getArticlePhoto().stream().map(DetailArticlePhotoRes::toDto).collect(Collectors.toList()))
            .build();
  }

  private static Boolean checkAttention(List<ArticleAttention> articleAttentions, AttentionType like) {
    if (articleAttentions.size() != 0) {
      for (ArticleAttention articleAttention : articleAttentions) {
        if (articleAttention.getAttentionType().equals(like)) {
          return true;
        }
      }
    }
    return false;

  }
}
