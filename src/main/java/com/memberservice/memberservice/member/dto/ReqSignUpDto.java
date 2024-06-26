package com.memberservice.memberservice.member.dto;

import com.memberservice.memberservice.common.enums.Role;
import com.memberservice.memberservice.member.entity.Member;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class ReqSignUpDto {
    @Email(message = "Invalid email format")
    private String email;
    private String password;
    private String role;
    private String name;
    private String address;
    private String profile_url;


    @Builder
    public ReqSignUpDto(String email, String password, String role, String name, String address, String profile_url) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.profile_url = profile_url;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public Member toEntity(Role role_) {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .role(role_)
                .name(this.name)
                .address(this.address)
                .profile_url(this.profile_url)
                .build();
    }
}
