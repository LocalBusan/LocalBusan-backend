package com.LocalBusan.LocalBusan.comment;

import com.LocalBusan.LocalBusan.Article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticle(Article article);

}
