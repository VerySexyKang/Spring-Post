package com.example.post.model.posts;

import com.example.post.model.users.User;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Post {
    private Long id;                    // 게시글 ID
    private String title;               // 제목
    private String content;             // 내용
    private User user;                  // 작성자
    private int views;                  // 조회수
    private LocalDateTime createTime;   // 작성일

    // 조회수 증가
    public void incrementViews() {
        this.views++;
    }
}
