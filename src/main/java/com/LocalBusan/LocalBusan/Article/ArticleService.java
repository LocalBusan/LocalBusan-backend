package com.LocalBusan.LocalBusan.Article;

import com.LocalBusan.LocalBusan.Category.*;
import com.LocalBusan.LocalBusan.region.*;
import com.LocalBusan.LocalBusan.signup.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RegionRepository regionRepository;

    public Article createArticle(ArticleRequest request) {
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = null;
        if (request.getCategory_id() != null) {
            category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Region region = null;
        if (request.getRegion_id() != null) {
            region = regionRepository.findById(request.getRegion_id())
                    .orElseThrow(() -> new RuntimeException("Region not found"));
        }

        Article article = new Article();
        article.setUser(user);
        article.setCategory(category);
        article.setRegion(region);
        article.setTitle(request.getTitle());
        article.setSubtitle(request.getSubtitle());
        article.setThumbnail_url(request.getThumbnail_url());
        article.setContent(request.getContent());
        article.setCreated_at(LocalDate.now());

        return articleRepository.save(article);
    }

    // 게시글 수정
    public Article updateArticle(Integer articleId, ArticleRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Category category = null;
        if (request.getCategory_id() != null) {
            category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Region region = null;
        if (request.getRegion_id() != null) {
            region = regionRepository.findById(request.getRegion_id())
                    .orElseThrow(() -> new RuntimeException("Region not found"));
        }

        article.setCategory(category);
        article.setRegion(region);
        article.setTitle(request.getTitle());
        article.setSubtitle(request.getSubtitle());
        article.setThumbnail_url(request.getThumbnail_url());
        article.setContent(request.getContent());
        article.setUpdated_at(LocalDate.now());

        return articleRepository.save(article);
    }
    // 게시글 삭제
    public void deleteArticle(Integer article_id) {
        Article article = articleRepository.findById(article_id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        articleRepository.delete(article);
    }

    // 게시글 목록 조회
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    // 게시글 조회 (ID로 검색)
    public Article getArticleById(Integer articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }
}
