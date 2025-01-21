package com.example.post.repository;

import com.example.post.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private static Long sequence =0L;
    private final Map<Long, User> store = new HashMap<>();

    // User 저장
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    // Id로 User 조회
    // Optional = 객체를 한번 감싸는 기능. null이 넘어올 수 있으니 null체크 필요
    // optional안의 값이 null이거나 user타입일 수 있음. return타입도 optional의 user타입
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // 모든 User 조회
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
