package com.LocalBusan.LocalBusan.Article;

import com.LocalBusan.LocalBusan.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/board")
@RestController
public class ArticleController {

    @Autowired
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 게시글 작성 (POST)
    @PostMapping
    public ResponseEntity<?> createArticle(
            @CookieValue(value = "jwt", required = false) String jwt, // 쿠키에서 JWT 추출
            @RequestBody ArticleRequest request) {
        try {
            if (jwt == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 토큰이 없습니다.");
            }

            System.out.println("Received Token: " + jwt); // 디버깅

            // 토큰에서 클레임 추출
            Claims claims = JwtUtil.extractToken(jwt);
            System.out.println("Extracted Claims: " + claims); // 디버깅

            // 사용자 정보 가져오기
            String userEmailString = claims.get("email", String.class);
            System.out.println("Extracted User ID: " + userEmailString); // 디버깅

            // 게시글 생성 요청 처리
            Article createdArticle = articleService.createArticle(userEmailString, request);

            // 생성된 게시물을 응답으로 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);

        } catch (Exception e) {
            e.printStackTrace(); // 디버깅
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    // 게시글 조회 (GET)
    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Integer id) {
        return articleService.getArticleById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
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

    // 게시글 작성 사이드
}

@Controller
class getHtml {
    @GetMapping("/postBoard")
    public String getPostBoard() {
        return "/postBoard";
    }
}