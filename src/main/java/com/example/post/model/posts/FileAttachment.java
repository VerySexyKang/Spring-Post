package com.example.post.model.posts;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FileAttachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFilename;    // 원본 파일 명
    private String storedFilename;      // 서버에 저장된 파일 명
    private long size;                  // 파일 크기

    @OneToOne
    @JoinColumn(name = "post_id")       // ForeignKey 칼럼의 이름을 정의
    private Post post;
}
