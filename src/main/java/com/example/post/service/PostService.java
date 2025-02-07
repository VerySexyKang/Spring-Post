package com.example.post.service;

import com.example.post.model.posts.FileAttachment;
import com.example.post.model.posts.Post;
import com.example.post.repository.PostRepository;
import com.example.post.util.FileAttachmentUtil;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true) // 읽기 전용으로 가져옴
@RequiredArgsConstructor
@Service
public class PostService {
    @Value("${file.upload.path}")
    private String uploadPath;
    // PostService 객체 생성 시점에 스프링 컨테이너가 자동으로 의존성을 주입 해준다
    private final PostRepository postRepository;

    // 글 저장
    // 커밋 롤백이 필요한 메서드 위에 Transactional 붙여야함
    @Transactional
    public Post savePost(Post post, MultipartFile file) {
        post.setCreateTime(LocalDateTime.now());

        // 첨부 파일이 존재하는지 확인
        if (file != null || !file.isEmpty()) {
            // 첨부 파일 저장
            String storedFilename = FileAttachmentUtil.uploadFile(file, uploadPath);
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setOriginalFilename(file.getOriginalFilename());
            fileAttachment.setStoredFilename(storedFilename);
            fileAttachment.setSize(file.getSize());
            // 양방향이기에 포스트와 파일 어테치먼트 양쪽에 넣어줌
            fileAttachment.setPost(post);
            post.setFileAttachment(fileAttachment);
        }

        post.setCreateTime(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    // 글 전체 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 아이디로 글 조회
    @Transactional
    public Post findPostById(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
//        if (findPost.isPresent()) {
//            findPost.get().incrementViews();
//            return findPost.get();
//        }
//        throws new IllegalArgumentException("게시글을 찾을 수 없습니다.");
//        return null;
        Post post = findPost.orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.incrementViews();
        return post;
    }

    // 글 삭제
    @Transactional
    public void removePost(Long postId) {
        // 글을 repo에서 읽어옴. 조회
//        Post findPost = postRepository.findPostById(postId);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            postRepository.delete(findPost.get());
        }
        postRepository.deleteById(postId);
    }

    // 글 수정
    @Transactional
    public void updatePost(Post updatePost, Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
        }

        }

    }

