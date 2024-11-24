package com.LocalBusan.LocalBusan.comment;

import com.LocalBusan.LocalBusan.signup.CustomUser;
import com.LocalBusan.LocalBusan.signup.User;
import com.LocalBusan.LocalBusan.signup.UserRepository;
import com.LocalBusan.LocalBusan.signup.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Comment-Controller", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/boards")
public class CommentController {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    @PostMapping("/{id}/comments")
    @Operation(summary = "댓글 작성", description = "댓글을 추가합니다.")
    public ResponseEntity<Void> addComment(Authentication auth, @PathVariable("id") Integer article_id, @RequestBody Map<String, String> data){
        CustomUser user = (CustomUser)auth.getPrincipal();
        User temp = userRepository.findByEmail(user.getUsername()).get();

        Comment comment = new Comment();
        comment.setContent(data.get("content"));
        comment.setArticle_id(article_id);
        comment.setUser_id(temp.user_id);

        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(Authentication auth, @PathVariable("id") Integer article_id, @PathVariable("commentId") Integer reply_id){
        Comment comment = commentRepository.getReferenceById(reply_id);

        commentRepository.delete(comment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
