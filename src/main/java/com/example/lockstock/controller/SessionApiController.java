package com.example.lockstock.controller;

import com.example.lockstock.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionApiController {

    @GetMapping("/api/check-session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Object userId = session.getAttribute(SessionConst.USER_ID);
        Object userName = session.getAttribute(SessionConst.USER_NAME);

        if (userId == null || userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        Map<String, String> sessionInfo = new HashMap<>();
        sessionInfo.put(SessionConst.USER_ID, userId.toString());
        sessionInfo.put(SessionConst.USER_NAME, userName.toString());

        return ResponseEntity.ok(sessionInfo);
    }
}