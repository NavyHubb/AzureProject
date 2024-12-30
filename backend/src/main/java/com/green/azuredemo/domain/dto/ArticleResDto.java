package com.green.azuredemo.domain.dto;

import com.green.azuredemo.domain.Article;
import java.time.LocalDateTime;

public record ArticleResDto(Integer id, String title, String content, LocalDateTime createdAt) {
    public static ArticleResDto from(Article article) {
        return new ArticleResDto(article.getId(), article.getTitle(), article.getContent(), article.getCreatedAt());
    }
}
