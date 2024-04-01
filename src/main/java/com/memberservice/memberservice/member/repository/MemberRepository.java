package com.memberservice.memberservice.member.repository;

import com.memberservice.memberservice.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
