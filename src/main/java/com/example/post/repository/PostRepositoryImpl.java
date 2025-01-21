package com.example.post.repository;

import com.example.post.model.posts.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final static Map<Long, Post> posts = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void savePost(Post post) {
        post.setId(++sequence);
        posts.put(post.getId(), post);
    }

    @Override
    public List<Post> findAllPost() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Post findPostById(Long postId) {
        return posts.get(postId);
    }

    @Override
    public void updatePost(Post updatePost) {

    }

    @Override
    public void deletePost(Long postId) {
        posts.remove(postId);

    }
}
