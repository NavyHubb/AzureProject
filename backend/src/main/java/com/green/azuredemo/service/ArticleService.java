package com.green.azuredemo.service;

import com.green.azuredemo.domain.Article;
import com.green.azuredemo.domain.dto.ArticleReqDto;
import com.green.azuredemo.domain.dto.ArticleResDto;
import com.green.azuredemo.repository.ArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResDto create(ArticleReqDto articleReqDto) {
        Article savedArticle = articleRepository.save(articleReqDto.toEntity());
        return ArticleResDto.from(savedArticle);
    }

    @Transactional(readOnly = true)
    public List<ArticleResDto> getAllArticles() {
        return articleRepository.findAll().stream()
            .map(ArticleResDto::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResDto getArticleById(Integer id) {
        return articleRepository.findById(id)
            .map(ArticleResDto::from)
            .orElseThrow(() -> new RuntimeException("Article not found with id " + id));
    }

    public ArticleResDto updateArticle(Integer id, ArticleReqDto ArticleReqDto) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found with id " + id));

        article.update(ArticleReqDto.title(), ArticleReqDto.content());
        Article savedArticle = articleRepository.save(article);
        return ArticleResDto.from(savedArticle);
    }

    public void deleteArticle(Integer id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Article not found with id " + id);
        }
    }

}
