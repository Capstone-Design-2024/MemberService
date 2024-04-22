package com.memberservice.memberservice.member.service;

import com.memberservice.memberservice.common.enums.Role;
import com.memberservice.memberservice.handler.CustomException;
import com.memberservice.memberservice.handler.StatusCode;
import com.memberservice.memberservice.kafka.dto.SyncMemberInfoDto;
import com.memberservice.memberservice.kafka.producer.MemberInfoProducer;
import com.memberservice.memberservice.member.dto.ReqSignInDto;
import com.memberservice.memberservice.member.dto.ReqSignUpDto;
import com.memberservice.memberservice.member.entity.Member;
import com.memberservice.memberservice.member.repository.MemberRepository;
import com.memberservice.memberservice.security.JwtCreator;
import com.memberservice.memberservice.security.dto.Token;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JwtCreator jwtCreator;
    private final MemberInfoProducer memberInfoProducer;


    @Override
    public Token getToken(ReqSignInDto reqSignInDto, String userAgent, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findByEmail(reqSignInDto.getEmail()).orElseThrow(() -> new CustomException(StatusCode.USERNAME_NOT_FOUND));
        if(!passwordEncoder.matches(reqSignInDto.getPassword(), member.getPassword()))
            throw new CustomException(StatusCode.INVALID_PASSWORD);
        Token token = jwtCreator.createToken(member);
        return token;
    }
    @Override
    @Transactional
    public void saveMemberInfo(ReqSignUpDto reqSignUpDto) {
        memberRepository.findByEmail(reqSignUpDto.getEmail()).ifPresent(
                memberInfo -> { throw new CustomException(StatusCode.REGISTERED_EMAIL); });
        Role role = Arrays.stream(Role.values())
                .filter(r -> r.name().equals(reqSignUpDto.getRole()))
                .findFirst()
                .orElseThrow(() -> new CustomException(StatusCode.INVALID_ROLE));

        Member member =reqSignUpDto.toEntity(role);
        memberRepository.save(member);
        syncMemberInfo(reqSignUpDto.getEmail());
    }

    private void syncMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        Map<String, Object> data = new HashMap<>();
        data.put("member_id", member.getMemberId());
        data.put("address", member.getAddress());
        data.put("role", member.getRole().toString());
        data.put("email", member.getEmail());
        data.put("password", member.getPassword());
        if (member.getProfile_url() == null) data.put("profile_url", "default");
        else data.put("profile_url", member.getProfile_url());
        data.put("name", member.getName());

        memberInfoProducer.memberInfoProducer(data);
    }
}
