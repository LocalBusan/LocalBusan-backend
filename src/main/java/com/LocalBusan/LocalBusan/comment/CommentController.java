package com.LocalBusan.LocalBusan.comment;

import com.LocalBusan.LocalBusan.Article.ArticleRepository;
import com.LocalBusan.LocalBusan.signup.CustomUser;
import com.LocalBusan.LocalBusan.signup.User;
import com.LocalBusan.LocalBusan.signup.UserRepository;
import com.LocalBusan.LocalBusan.signup.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Comment-Controller", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/boards")
public class CommentController {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @PostMapping("/{id}/comments")
    @Operation(summary = "댓글 작성", description = "댓글을 추가합니다.")
    public ResponseEntity<Void> addComment(Authentication auth, @PathVariable("id") Integer article_id, @RequestBody Map<String, String> data){
        CustomUser user = (CustomUser)auth.getPrincipal();
        User temp = userRepository.findByEmail(user.getUsername()).get();

        Comment comment = new Comment();
        comment.setContent(data.get("content"));
        comment.setUser_id(temp.userId);
        comment.setArticle(articleRepository.findById(article_id).get());

        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //댓글 삭제
    @DeleteMapping("/{id}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(Authentication auth, @PathVariable("id") Integer article_id, @PathVariable("commentId") Integer reply_id){
        Comment comment = commentRepository.getReferenceById(reply_id);

        commentRepository.delete(comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //댓글 수정
    @PatchMapping("/{id}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public ResponseEntity<Void> updateComment(
            Authentication auth,
            @PathVariable("id") Integer article_id,
            @PathVariable("commentId") Integer comment_id,
            @RequestBody Map<String, String> data) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        User temp = userRepository.findByEmail(user.getUsername()).get();
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (!comment.getUser_id().equals(temp.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 댓글 내용 업데이트
        comment.setContent(data.get("content"));
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //댓글 조회
    @GetMapping("/{id}/comments")
    @Operation(summary = "댓글 조회", description = "특정 게시글에 대한 댓글 목록을 조회합니다.")
    public ResponseEntity<List<CommentRequest>> getCommentsByArticle(@PathVariable("id") Integer article_id) {
        List<Comment> comments = commentRepository.findByArticle(articleRepository.findById(article_id).get());

        if (comments == null || comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<CommentRequest> ret = new ArrayList<>();

        for(Comment c : comments){
            CommentRequest commentRequest = new CommentRequest();
            User user = userRepository.findByUserId(c.getUser_id()).get();
            commentRequest.setNickname(user.getNickname());
            commentRequest.setCreated_at(c.getCreated_at());
            commentRequest.setContent(c.getContent());
            commentRequest.setReply_id(c.getReply_id());
            ret.add(commentRequest);
        }

        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }


}