package com.LocalBusan.LocalBusan.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    // 추가적인 쿼리 메서드 작성 가능
    @Query("SELECT * FROM Article ORDER BY DESC")
    List<Article> findAllOrderByIdDesc();
}
