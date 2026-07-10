package com.example.lockstock.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String userId;
    private String password;
    private String userName;
}
