package com.memberservice.memberservice.member.service;

import com.memberservice.memberservice.member.dto.ReqSignInDto;
import com.memberservice.memberservice.member.dto.ReqSignUpDto;
import com.memberservice.memberservice.security.dto.Token;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface MemberService {
    Token getToken(ReqSignInDto reqSignInDto, String userAgent, PasswordEncoder passwordEncoder);
    void saveMemberInfo(ReqSignUpDto reqSignUpDto);
}
