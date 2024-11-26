package com.LocalBusan.LocalBusan.comment;

import com.LocalBusan.LocalBusan.Article.Article;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    public Integer reply_id;
    public Integer user_id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    public Article article;
    public String content;
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @PrePersist
    public void onCreate() {
        created_at = new Date(); // 저장 시 현재 시간 자동 설정
    }
}
