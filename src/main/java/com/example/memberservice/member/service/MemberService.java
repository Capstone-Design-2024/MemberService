package com.example.memberservice.member.service;

import com.example.memberservice.member.dto.ReqSignInDto;
import com.example.memberservice.member.dto.ReqSignUpDto;
import com.example.memberservice.security.dto.Token;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface MemberService {
    Token getToken(ReqSignInDto reqSignInDto, String userAgent, PasswordEncoder passwordEncoder);
    void saveMemberInfo(ReqSignUpDto reqSignUpDto);
}
