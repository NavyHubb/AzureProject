package com.green.azuredemo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.green.azuredemo.domain.Article;
import com.green.azuredemo.domain.dto.ArticleReqDto;
import com.green.azuredemo.domain.dto.ArticleResDto;
import com.green.azuredemo.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글 생성")
    void saveArticle_shouldReturnSavedArticle() {
        // Given
        ArticleReqDto articleReqDto = new ArticleReqDto("Test Title", "Test Content");
        Article article = new Article(null, "Test Title", "Test Content");
        Article savedArticle = new Article(1, "Test Title", "Test Content");

        when(articleRepository.save(any(Article.class))).thenReturn(savedArticle);

        // When
        ArticleResDto result = articleService.create(articleReqDto);

        // Then
        assertNotNull(result);
        assertEquals(savedArticle.getId(), result.id());
        assertEquals(savedArticle.getTitle(), result.title());
        assertEquals(savedArticle.getContent(), result.content());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Nested
    @DisplayName("게시글 조회")
    class testGet {

        @Test
        @DisplayName("ID로 조회 성공")
        void getArticleById_shouldReturnArticle() {
            // Given
            Integer articleId = 1;
            Article article = new Article(articleId, "Test Title", "Test Content");

            when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

            // When
            ArticleResDto result = articleService.getArticleById(articleId);

            // Then
            assertNotNull(result);
            assertEquals(article.getId(), result.id());
            assertEquals(article.getTitle(), result.title());
            assertEquals(article.getContent(), result.content());
            verify(articleRepository, times(1)).findById(articleId);
        }

        @Test
        @DisplayName("ID로 조회 실패")
        void getArticleById_shouldThrowExceptionIfNotFound() {
            // Given
            Integer articleId = 1;

            when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                articleService.getArticleById(articleId)
            );
            assertEquals("Article not found with id " + articleId, exception.getMessage());
            verify(articleRepository, times(1)).findById(articleId);
        }

    }

    @Test
    @DisplayName("게시글 수정")
    void updateArticle_shouldUpdateAndReturnUpdatedArticle() {
        // Given
        Integer articleId = 1;
        ArticleReqDto articleReqDto = new ArticleReqDto("Updated Title", "Updated Content");
        Article existingArticle = new Article(articleId, "Old Title", "Old Content");

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(existingArticle));
        when(articleRepository.save(any(Article.class))).thenReturn(existingArticle);

        // When
        ArticleResDto result = articleService.updateArticle(articleId, articleReqDto);

        // Then
        assertNotNull(result);
        assertEquals(articleReqDto.title(), result.title());
        assertEquals(articleReqDto.content(), result.content());
        verify(articleRepository, times(1)).findById(articleId);
        verify(articleRepository, times(1)).save(existingArticle);
    }

    @Nested
    @DisplayName("게시글 삭제")
    class testDelete {

        private Integer articleId;
        private Article article;

        @BeforeEach
        void setup() {
            articleId = 1;
            article = getArticle();
        }

        @Test
        @DisplayName("[성공]")
        void success() {
            // given
            when(articleRepository.existsById(articleId)).thenReturn(true);

            // when
            articleService.deleteArticle(articleId);

            // then
            verify(articleRepository, times(1)).deleteById(articleId);
        }
    }

    Article getArticle() {
        return Article.builder()
            .id(1)
            .title("title")
            .content("content")
            .build();
    }

}