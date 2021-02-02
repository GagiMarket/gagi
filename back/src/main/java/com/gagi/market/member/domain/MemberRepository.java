package com.gagi.market.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberEmailAndMemberPw(String memberEmail, String memberPw);
    Optional<Member> findMemberByMemberEmail(String memberEmail);
    void deleteMemberByMemberEmail(String memberEmail);
}
