package com.example.post.model.posts;

import com.example.post.model.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 게시글 ID
    private String title;               // 제목

    @Lob
    private String content;             // 내용(VARCHAR 255)

    // 연관관계 맺어야함. 외례키를 기자고 있는 쪽이 주인 >> ManyToOne
    // 지연로딩 LAZY
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩으로 변경
    @JoinColumn(name = "user_id")
    private User user;                  // 작성자
    private int views;                  // 조회수
    private LocalDateTime createTime;   // 작성일

    // 조회수 증가
    public void incrementViews() {
        this.views++;
    }
}
