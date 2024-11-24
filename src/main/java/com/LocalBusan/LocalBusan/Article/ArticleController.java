package com.LocalBusan.LocalBusan.Article;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 게시글 작성 (POST)
    @PostMapping("/api/boards")
    public Article createArticle(@RequestBody ArticleRequest request) {
        System.out.println("컨트롤러 진입");
        return articleService.createArticle(request);
    }

    /*
    //샘플 article 생성
    @PostConstruct
    public Article createSampe() {
        ArticleRequest request = new ArticleRequest(1, 1, 1, "a", "b", "c", "d");
        System.out.println("컨트롤러 진입");
        return articleService.createArticle(request);
    }

     */
    // 게시글 조회 (GET)
    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Integer id) {
        return articleService.getArticleById(id);
    }

    // 게시글 수정 (PUT)
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable("id") Integer id, @RequestBody ArticleRequest request) {
        return articleService.updateArticle(id, request);
    }

    // 게시글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();  // 성공적으로 삭제되었음을 나타냄
    }

    // 게시글 목록 조회 (GET)
    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
}
