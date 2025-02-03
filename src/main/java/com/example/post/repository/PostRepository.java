package com.example.post.repository;

import com.example.post.model.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
