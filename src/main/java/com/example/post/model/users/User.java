package com.example.post.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class User {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;                // 회원정보를 구분하는 ID
        private String username;        // 로그인 아이디
        private String password;        // 로그인 패스워드
        private String name;            // 사용자 이름
        @Enumerated(EnumType.STRING)
        private GenderType gender;
        private LocalDate birthday;
        private String email;
}
