package com.example.post.controller;

import com.example.post.model.posts.Comment;
import com.example.post.model.posts.CommentResponseDto;
import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.CommentService;
import com.example.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class CommentController {
    // final 이 붙어야 @RequiredArgsConstructor 에서 생성자 주입이 가능
    private final PostService postService;
    private final CommentService commentService;

    /*
     * HTTP 요청 방식
     * GET : 일반적으로 데이터 조회
     * POST : 데이터 저장, 등록
     * PUT : 교체
     * PATCH : 일부 교체(수정)
     * DELETE : 삭제
     */

    // 댓글 작성(POST)
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable(name = "postId") Long postId,
            @RequestBody Comment comment,
            @SessionAttribute(name = "loginUser") User loginUser) throws JsonProcessingException {

        
        log.info("Comment: {}", comment);

//        // 임시로 Mapping 값을 가져올 때 ObjectMapper 사용
//        ObjectMapper objectMapper = new ObjectMapper();
//        Comment value = objectMapper.readValue(comment, Comment.class);
//        log.info("Value: {}", value);

        // 어떤 게시글에 대한 댓글인지
        Post post = postService.findPostById(postId);
        // 누가 썼는지 세션에서 받아온다.
        comment.setPost(post);
        comment.setUser(loginUser);
        log.info("Comment: {}", comment);
        commentService.addComment(comment);

        CommentResponseDto responseDto = comment.toResponseDto();

        return ResponseEntity.ok(responseDto);
    }

    // 댓글 조회(GET)
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments() {
        return ResponseEntity.ok(null);
    }

    // 댓글 수정(PUT or PATCH)
    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<List<Comment>> editComment() {
        return ResponseEntity.ok(null);
    }
    // 댓글 삭제(DELETE)
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment() {
        return ResponseEntity.ok(null);
    }
}
