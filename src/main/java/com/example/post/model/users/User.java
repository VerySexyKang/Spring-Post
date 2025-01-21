package com.example.post.model.users;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
        private long id;                // 회원정보를 구분하는 ID
        private String username;        // 로그인 아이디
        private String password;        // 로그인 패스워드
        private String name;            // 사용자 이름
        private GenderType gender;
        private LocalDate birthday;
        private String email;
}
