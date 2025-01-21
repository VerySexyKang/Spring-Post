package com.example.post.controller;


import com.example.post.model.User;
import com.example.post.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor // 생성자 주입 어노테이션
public class UserController {

    // 선언만 해놓으면 생성자 만들어 줌
    private final UserService userService;

    // 회원가입 페이지 요청 처리
    @GetMapping(path = "register")
    public String register() {
        return "register";
    }

    // 이름 이메일 입력 후 가입하기를 클릭하면 상뇽자가 입력한 이름과 이메일을 스프링에서 로그로 출력
//    @PostMapping(path = "register_v3")
//    public String register_V3(@RequestParam (name = "name") String name,
//                              @RequestParam (name = "email") String email) {
//
//        log.info("param name: {}", name);
//        log.info("param {}",  email);
//        return "register_success";
//    }

    /**
     * 컨트롤러의 역할
     * URL 매핑, 파라미터 확인, 검증
     * 비즈니스 로직 >> 다른 클래스(서비스 레이어)에 넘기는 것이 좋음
     * 리턴(데이터,HTML)
     * DB와 연동 작업이 필요한 경우
     * Repository레이어에 넘겨 작업
     * 역할 = 데이터베이스 연동
     * 레이어는 컨트롤러, 서비스, 레퍼지토리의 3가지 레이어로 작업
     * 회원가입 요청 처리
     */
    @PostMapping(path = "register_v3")
    public String register_V3(@ModelAttribute User user) {
        log.info("user: {}", user);
        User registedUser = userService.registerUser(user);
        log.info("registedUser: {}", registedUser);

        return "register_success";
    }

    // 아이디로 회원 정보 조회 ex) /user-details/{사용자ID(path value)}
    // 사용자ID로 조회 후 리턴을 user_detail.html을 보여준다
    // 타임리프 user_detail에 적용
//    @GetMapping(path = "/user-details/{id}")
//    public String findUserId(@PathVariable (name = "id") Long id, Model model) {
//        User getUser = userService.getUserById(id);
//        if(getUser != null) {
//            model.addAttribute("id", getUser.getId());
//            model.addAttribute("name", getUser.getName());
//            model.addAttribute("email", getUser.getEmail());
//        }
//        return "user_detail";
//    }

    // 그냥 URL로 요청하는 것이니 GetMapping
    // 변경 같은 거는 PosstMapping
    @GetMapping(path = "user-detail/{id}")
    // PathVariable 페러미터 안에 name은 경로변수 중괄호 안의 이름과 같아야함
    public String userDetails(@PathVariable(name = "id") Long id, Model model) {

        // User타입인 이유 = 무조건 user로 service에서 넘어옴
        User user = userService.getUserById(id);
        // 검색한 User정보를 Model에 담는다
        model.addAttribute("user", user);

        return "user_detail";
    }


    // 회원목록보기 >> 유저 이름을 클릭하면 회원 상세정보 보기
    @GetMapping("user-list")
    public String userList(Model model) {
        List<User> allList = userService.getAllUsers();
        model.addAttribute("users", allList);
        return "user_list";
    }

//    @GetMapping("user-list")
//    public String userList(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//
//        return "user_list";
//    }
    
}
