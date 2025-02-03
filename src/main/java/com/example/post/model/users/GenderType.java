package com.example.post.model.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 생성자에 매게변수로 넣어주는 것
@RequiredArgsConstructor
@Getter
public enum GenderType {
    MALE("남성"),
    FEMALE("여성");

    private final String description;
}
