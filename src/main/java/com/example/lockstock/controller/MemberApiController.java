package com.example.lockstock.controller;

import com.example.lockstock.dto.request.LoginRequestDto;
import com.example.lockstock.dto.request.MemberJoinRequestDto;
import com.example.lockstock.dto.response.LoginResponseDto;
import com.example.lockstock.dto.response.MemberJoinResponseDto;
import com.example.lockstock.service.MemberService;
import com.example.lockstock.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto dto) {
        memberService.join(dto);
        return new MemberJoinResponseDto("/members/login");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto, HttpSession session) {
        return memberService.login(dto).map(member -> {
            session.setAttribute(SessionConst.USER_NAME, member.getUserName());
            session.setAttribute(SessionConst.USER_ID, member.getUserId());
            return LoginResponseDto.success();
        }).orElseGet(LoginResponseDto::fail);
    }

}
