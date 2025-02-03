package com.example.post.controller;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
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
    public String createPostForm(
            // 세션에 저장된 데이터를 조회한다.
            @SessionAttribute(name = "loginUser", required = false) User loginUser) {
        // 사용자가 로그인을 했는지 체크
        log.info("loginUser: {}", loginUser);
        //로그인을 하지 않았으면 로그인 페이지로 리다이렉트
        if (loginUser == null) {
            return "redirect:/users/login";
        }
        // 게시글 작성 페이지 뷰 이름 return
        return "posts/create";
    }

    // 게시글 등록
    @PostMapping("posts")
    public String savePost(@ModelAttribute Post post,
                           @SessionAttribute(name = "loginUser") User loginUser) {
        log.info("Post created: {}", post);
        // 세션에서 사용자 정보를 가져와서 Post 객체에 넣어준다.
        post.setUser(loginUser);

        Post savedPost = postService.savePost(post);
        log.info("Post saved: {}", savedPost);
        return "redirect:/posts";
    }

    // 게시글 목록 조회
    /* 게시글 등록과 Mapping값이 똑같으나 되는 이유
    등록은 Post방식 조회는 Get방식이라 가능. 방식이 다르면 됨 */
    @GetMapping("posts")
    public String listPosts(
            @SessionAttribute(name = "loginUser", required = false) User loginUser,
            Model model) {

        //로그인하지 않았음
        if (loginUser == null) {
            return "redirect:/users/login";
        }

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
    @GetMapping("/posts/remove/{postId}")
    public String removePost(@PathVariable(name = "postId") Long postId,
                             @SessionAttribute(name = "loginUser") User loginUser) {

        // 삭제하려고 하는 게시글이 로그인한 사용자가 작성한 글인지 확인
        Post findPost = postService.findPostById(postId);
        if (findPost == null || findPost.getUser().getId() != loginUser.getId()) {
            return "redirect:/posts";
        }
        postService.removePost(postId);

        return "redirect:/posts";
    }

    // 수정하기 이동
    @GetMapping("posts/edit/{postId}")
    public String editPostForm(Model model,
                               @PathVariable(name = "postId") Long postId) {
        model.addAttribute("post", postService.findPostById(postId));
        return "posts/edit";
    }

    // 게시글 수정
    @PostMapping("posts/edit/{postId}")
    public String editPost(@SessionAttribute(name = "loginUser") User loginUser,
                           @PathVariable(name = "postId") Long postId,
                           @ModelAttribute Post updatePost) {
        log.info("updatePost: {}", updatePost);

        // 수정하려는 게시글이 존재하고 로그인 사용자와 게시글의 작성자가 같은지 확인
        Post postById = postService.findPostById(postId);
        if (postById.getUser().getId() == loginUser.getId()) {
            postService.updatePost(updatePost, postId);
        }

        return "redirect:/posts";
    }

}


