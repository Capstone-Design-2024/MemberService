package com.memberservice.memberservice.member.service;

import com.memberservice.memberservice.common.enums.Role;
import com.memberservice.memberservice.handler.CustomException;
import com.memberservice.memberservice.handler.StatusCode;
import com.memberservice.memberservice.member.dto.ReqSignInDto;
import com.memberservice.memberservice.member.dto.ReqSignUpDto;
import com.memberservice.memberservice.member.entity.Member;
import com.memberservice.memberservice.member.repository.MemberRepository;
import com.memberservice.memberservice.security.JwtCreator;
import com.memberservice.memberservice.security.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JwtCreator jwtCreator;


    @Override
    public Token getToken(ReqSignInDto reqSignInDto, String userAgent, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findByEmail(reqSignInDto.getEmail()).orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));
        if(!passwordEncoder.matches(reqSignInDto.getPassword(), member.getPassword()))
            throw new CustomException(StatusCode.INVALID_PASSWORD);
        Token token = jwtCreator.createToken(member);
        return token;
    }
    @Override
    public void saveMemberInfo(ReqSignUpDto reqSignUpDto) {
        memberRepository.findByEmail(reqSignUpDto.getEmail()).ifPresent(
                memberInfo -> { throw new CustomException(StatusCode.REGISTERED_EMAIL); });
        Role role = Arrays.stream(Role.values())
                .filter(r -> r.name().equals(reqSignUpDto.getRole()))
                .findFirst()
                .orElseThrow(() -> new CustomException(StatusCode.INVALID_ROLE));

        Member member =reqSignUpDto.toEntity(role);
        memberRepository.save(member);
    }
}
