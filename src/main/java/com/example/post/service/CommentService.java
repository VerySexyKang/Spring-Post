package com.example.post.service;

import com.example.post.model.posts.Comment;
import com.example.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {
    // 생성자 주입은 항상 final
    private final CommentRepository commentRepository;

    // 댓글 등롱
    @jakarta.transaction.Transactional
    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

    // 댓글 전체 조회
    public List<Comment> getCommentsByPostId(Long postId) {
        // 게시글 ID에 대한 모든 댓글 목록을 조회
        return commentRepository.findAllByPostId(postId);
    }

    // 댓글 조회
    public Comment getComment(Long commentId) {
        // 댓글 ID에 대한 내용을 조회
        return null;
    }

    // 댓글 수정
    public void editComment(Comment updatedComment) {

    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {

    }


}
