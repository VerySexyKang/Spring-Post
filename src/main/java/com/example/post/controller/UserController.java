package com.example.post.controller;


import com.example.post.model.users.User;
import com.example.post.model.users.UserCreateDto;
import com.example.post.model.users.UserLoginDto;
import com.example.post.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor // 생성자 주입 어노테이션
public class UserController {

    // 선언만 해놓으면 생성자 만들어 줌
    private final UserService userService;

    // 회원가입 페이지 요청 처리
    @GetMapping(path = "users/register")
    public String register(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "users/register";
    }

    // 이름 이메일 입력 후 가입하기를 클릭하면 상뇽자가 입력한 이름과 이메일을 스프링에서 로그로 출력
    /*    @PostMapping(path = "register_v3")
     *    public String register_V3(@RequestParam (name = "name") String name,
     *                              @RequestParam (name = "email") String email) {
     *
     *        log.info("param name: {}", name);
     *        log.info("param {}",  email);
     *        return "register_success";
     */

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
    // 회원가입 요청처리
    // ModelAttribute는 자동으로 Model에 담음. 아래 같은 경우는 userCreateDto에 자동으로 담음.
    @PostMapping(path = "users/register")
    public String register_V3(@Validated @ModelAttribute UserCreateDto userCreateDto,
                              BindingResult bindingResult) {
        // 유효성 검증이 실패했는지 확인
        // validation에 실패한 값이 bindingResult에 들어있음
        if (bindingResult.hasErrors()) {
            log.info("유효성 검증 실패");
            return "users/register";
        }

        // username(ID) 중복 확인
        if(userService.getUserByUsername(userCreateDto.getUsername()) != null) {
            // 이미 사용중인 username이 존재한다.
            // 에러코드는 errors.properties 파일에 정의된 에러 코드와 메시지를 사용한다.
            bindingResult.reject("duplicate.username");
            return "users/register";
        }

        log.info("user: {}", userCreateDto);

        // userCreateDto -> user 타입으로 변환
        User registedUser = userService.registerUser(userCreateDto.toEntity());
        log.info("registedUser: {}", registedUser);

        return "redirect:/";
    }

    // 로그인 페이지 이동
    @GetMapping("users/login")
    public String login(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "users/login";
    }

    // 로그인
    @PostMapping("users/login")
    public String login(
            @Validated @ModelAttribute UserLoginDto userLoginDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL) {

        log.info("redirectURL: {}", redirectURL);

        // 로그인 정보 검증에 실패하면 로그인 페이지로 돌아간다.
        if (bindingResult.hasErrors()){
            return "users/login";
        }
        log.info("user: {}", userLoginDto);

        // username에 해당하는 User 객체를 찾는다.
        User findUser = userService.getUserByUsername(userLoginDto.getUsername());
        log.info("findUser: {}", findUser);

        // 사용자가 입력한 username, password 정보가 데이터베이스의User 정보와 일치하는지 확인
        if (findUser == null || !findUser.getPassword().equals(userLoginDto.getPassword())) {
            bindingResult.reject("loginFailed", "아이디또는 패스워드가 다릅니다.");
            return "/users/login";
        }

        // Request 객체에 저장돼 있는 Session 객체를 받아온다.
        HttpSession session = request.getSession();
        // session 에 로그인 정보를 저장한다.
        session.setAttribute("loginUser", findUser);

        return "redirect:" + redirectURL;
    }

    // 로그아웃
    @GetMapping("/users/logout")
    // HttpSession으로 session을 그냥 받아올 수 있음
    public String logout(HttpSession session) {
        // session.setAttribute("loginUser", null);
        // 세션의 데이터를 모두 삭제
        session.invalidate();
        return "redirect:/";
    }

    // 세선 저장 확인
    @ResponseBody
    @GetMapping("loginCheck")
    public String loginCheck(HttpServletRequest request) {
        // Requesst 객체에 저장돼 있는 Session 객체를 받아온다.
        HttpSession session = request.getSession();
        // session에 저장된 loginUsername 정보를 찾는다.
        String loginUsername = (String) session.getAttribute("loginUsername");
        log.info("loginUsername: {}", loginUsername);

        return "ok";
    }


}
