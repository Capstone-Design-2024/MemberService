package com.memberservice.memberservice.kafka.dto;

import com.memberservice.memberservice.common.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SyncMemberInfoDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String profileUrl;
    private Long memberId;
    private Role role;

    @Builder
    public SyncMemberInfoDto(String name, String email, String password, String address, String profileUrl, Long memberId, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.profileUrl = profileUrl;
        this.memberId = memberId;
        this.role = role;
    }
}
