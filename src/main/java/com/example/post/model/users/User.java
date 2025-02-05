package com.example.post.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder // 빌더 패턴을 사용하기 위함
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
@NoArgsConstructor // 기본 생성자 (아무 것도 받지않는 생성자)
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
