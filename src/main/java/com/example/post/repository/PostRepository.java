package com.example.post.repository;

import com.example.post.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {
    // 어차피 인터페이스의 접근제어자는 public이기 때문에 굳이 안붙여도 됨
    // 글 등록
    void savePost(Post post);

    // 글 전체 조회
    List<Post> findAllPost();

    // 아이디로 글 조회
    Post findPostById(Long postId);

    // 글 수정
    void updatePost(Post updatePost);

    // 글 삭제
    void deletePost(Long postId);

}
