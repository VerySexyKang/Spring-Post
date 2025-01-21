package com.example.post.controller;

import com.example.post.model.Post;
import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;

    // 게시글 작성 페이지 이동
    @GetMapping("posts/create")
    public String createPostForm() {
        // 게시글 작성 페이지 뷰 이름 return
        return "posts/create";
    }

    // 게시글 등록
    @PostMapping("posts")
    public String savePost(@ModelAttribute Post post) {
        log.info("Post created: {}", post);
        Post savedPost = postService.savePost(post);
        log.info("Post saved: {}", savedPost);
        return "redirect:/posts";
    }

    // 게시글 목록 조회
    /* 게시글 등록과 Mapping값이 똑같으나 되는 이유
    등록은 Post방식 조회는 Get방식이라 가능. 방식이 다르면 됨 */
    @GetMapping("posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);

        return "posts/list";
    }

    // 게시글 조회
    @GetMapping("posts/{postId}")
    public String viewPost(@PathVariable(name = "postId") Long postId,
                           Model model) {
       Post findPost = postService.findPostById(postId);
        model.addAttribute("post", findPost);
        return "posts/view";
    }

    // 게시글 삭제
    @PostMapping("/posts/remove/{postId}")
    public String removePost(@PathVariable(name = "postId") Long postId,
                             @RequestParam(name = "password") String password) {
        postService.removePost(postId, password);
        return "redirect:/posts";
    }

}
