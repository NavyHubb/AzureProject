package com.green.azuredemo.domain.dto;

import com.green.azuredemo.domain.Article;

public record ArticleReqDto(String title, String content) {
    public Article toEntity() {
        return new Article(null, title, content);
    }
}