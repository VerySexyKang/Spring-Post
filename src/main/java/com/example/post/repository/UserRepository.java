package com.example.post.repository;

import com.example.post.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
// interface로 변경해보길
public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 회원 정보 조회 -> 쿼리 메소드
    User findByUsername(String username);
}