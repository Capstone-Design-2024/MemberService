package com.memberservice.memberservice.member.entity;


import com.memberservice.memberservice.baseTime.BaseTimeEntity;
import com.memberservice.memberservice.common.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name="member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "profileURL")
    private String profile_url;

    @Builder
    public Member(Long memberId, String email, String password, Role role, String name, String address, String profile_url) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.profile_url = profile_url;
    }
}
