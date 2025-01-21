package com.example.post.service;

import com.example.post.model.posts.Post;
import com.example.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    // PostService 객체 생성 시점에 스프링 컨테이너가
    // PostService 객체 생성 시점에 스프링 컨테이너가 자동으로 의존성을 주입 해준다
    private final PostRepository postRepository;

    // 글 저장
    public Post savePost(Post post) {
        post.setCreateTime(LocalDateTime.now());
        postRepository.savePost(post);
        return post;
    }

    // 글 전체 조회
    public List<Post> findAllPosts() {
        return postRepository.findAllPost();
    }

    // 아이디로 글 조회
    public Post findPostById(Long postId) {
        Post findPost = postRepository.findPostById(postId);
        // 조회수 증가
        findPost.incrementViews();
        return findPost;
    }

    // 글 삭제
    public void removePost(Long postId, String password) {
        // 글을 repo에서 읽어옴. 조회
        Post findPost = postRepository.findPostById(postId);
        if (findPost != null && findPost.getPassword().equals(password)) {
            postRepository.deletePost(postId);
        }

    }

}
