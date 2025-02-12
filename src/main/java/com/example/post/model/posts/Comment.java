package com.example.post.model.posts;

import com.example.post.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;     // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;          // 댓글이 속한 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;          // 댓글 작성자

    public CommentResponseDto toResponseDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .content(this.content)
                .postId(this.post.getId())
                .userId(this.user.getId())
                .username(this.user.getUsername())
                .build();
    }
}
