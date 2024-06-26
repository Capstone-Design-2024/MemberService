package com.memberservice.memberservice.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class ReqSignInDto {
    @Email(message = "Invalid email format")
    private String email;
    private String password;

    @Builder
    public ReqSignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
