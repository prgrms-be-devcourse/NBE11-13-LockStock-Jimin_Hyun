package com.example.lockstock.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "sign-up";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        // * "sign-in" 뷰를 바로 반환하지 않고 redirect 하는 이유
        // 그냥 sign-in 하면 html만 바뀔뿐 url을 안 바뀜 그래서 새로고침하면 getMapping logout 또 실행 됨
        // 상태를 바꾸는 요청(로그아웃) 뒤엔 리다이렉트해서, 새로고침 시 로그아웃이 재실행되는 것을 막고
        // 주소창도 /members/login을 맞춘다.


        return "redirect:/members/login";
    }
}