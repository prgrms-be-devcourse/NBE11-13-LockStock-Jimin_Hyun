package com.example.lockstock.service;

import com.example.lockstock.domain.entity.Member;
import com.example.lockstock.domain.repository.MemberRepository;
import com.example.lockstock.dto.request.LoginRequestDto;
import com.example.lockstock.dto.request.MemberJoinRequestDto;
import com.example.lockstock.exception.DuplicateUserIdException;
import com.example.lockstock.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        if(memberRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("회원가입 아이디가 중복됩니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }

    public Optional<Member> login(LoginRequestDto dto) {
        return memberRepository.findByUserId(dto.getUserId()).filter(member -> member.getPassword().equals(dto.getPassword()));
    }
}
