package com.example.post.service;

import com.example.post.model.User;
import com.example.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    /*
     * 의존성 주입 방법
     * 1. 필드 주입
     * 2. 생성자 주입
     * 3. 세터 주입
     */
    // 유저가 생성하지 않음. new 써서 하는 그거
    // @Autowired를 어디에 주느냐에 따라 달라짐
    // 필드 주입
//    @Autowired
    private final UserRepository userRepository;

    // 생성자 주입 >> 테스트에 용이 >> 가짜 레퍼지토리객체(mog객체)를 만들어 서비스만 테스트 가능
    // 이것을 추천
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    // 세터 주입 >> 일반적으로 이걸 사용
    // 세터 주입방식은 불변객체를 받을 수 있음
//    @Autowired
//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    // 사용자 등록
    public User registerUser(User user) {
        return userRepository.save(user);
    }

//    public User getUserById(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        // user에 값이 있는지 확인. Optional에서만 쓸 수 있음
//        if(user.isPresent()) {
//            return user.get();
//        }
//        return null;
//    }

    // ID로 사용자 조회
    public User getUserById(Long id) {
        Optional<User> result = userRepository.findById(id);
        // optional로 받은 값이 null이 아니면 true로 반환한다는 뜻 / 반대는 .isEmpty 비어있으면 true
        if (result.isPresent()) {
            // user를 꺼내 return
            return result.get();
        }
        // 아니면 Exception 발생시킴
        throw new RuntimeException("유저를 찾을 수 없음");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 전체 회원정보 조회
    public List<User> userList() {
        return userRepository.findAll();
    }

}
