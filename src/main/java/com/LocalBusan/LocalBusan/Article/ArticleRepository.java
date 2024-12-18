package com.LocalBusan.LocalBusan.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    // 추가적인 쿼리 메서드 작성 가능
}
