package com.example.lockstock.mapper;


import com.example.lockstock.domain.entity.Member;
import com.example.lockstock.dto.request.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberJoinRequestDto dto) {
        return Member.builder().userId(dto.getUserId()).password(dto.getPassword()).userName(dto.getUserName()).build();
    }
}
