package com.example.post.repository;

import com.example.post.model.posts.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 1. JPQL을 사용하는 방법
    @Query("select p from Post p order by p.createTime desc")
    List<Post> findAllPosts();

    // 2. 쿼리 메소드를 사용하는 방법
    Page<Post> findAllByOrderByCreateTimeDesc(Pageable pageable);

}
