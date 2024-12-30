package com.green.azuredemo.controller;

import com.green.azuredemo.domain.dto.ArticleReqDto;
import com.green.azuredemo.domain.dto.ArticleResDto;
import com.green.azuredemo.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResDto> createArticle(@RequestBody ArticleReqDto articleReqDto) {
        ArticleResDto savedArticle = articleService.create(articleReqDto);
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResDto>> getAllArticles() {
        List<ArticleResDto> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResDto> getArticleById(@PathVariable Integer id) {
        ArticleResDto article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResDto> updateArticle(
        @PathVariable Integer id,
        @RequestBody ArticleReqDto articleFormDto
    ) {
        ArticleResDto updatedArticle = articleService.updateArticle(id, articleFormDto);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}
