package com.example.post.model.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// 회원가입을 받기 위한 전용 클래스
/*
UserCreateDto를 만든 이유
회원가입, 회원 정보 수정을 위해선 User의 id는 필요 없음. (사용자에게 받을게 아니기 때문)
회원가입을 위한 전용 클래스를 지정하기 위함, 기존의 User 클래스와 같은 조건으로 유효성 검사를 할 순 없음
 */


@Data
public class UserCreateDto {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    private String password;
    private String name;
    private GenderType gender;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String email;

    // 빌더 패턴 사용 (매서드 체이닝)
    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .name(this.name)
                .gender(this.gender)
                .birthday(this.birthDate)
                .email(this.email)
                .build();

//        User user = new User();
//        user.setUsername(this.getUsername());
//        user.setPassword(this.getPassword());
//        user.setEmail(this.getEmail());
//        user.setName(this.getName());
//        user.setBirthday(this.getBirthDate());
//        user.setGender(this.getGender());
//        return user;
    }


}
