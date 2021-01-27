package com.gagi.market.member.service;

import com.gagi.market.member.domain.Member;

import java.util.List;

public interface MemberService {
    Member createMember(Member member);
    void login(String memberEmail, String memberPw);
    void logout();
    List<Member> findMembers();
    Member findMemberByMemberEmail(String memberEmail);
    Member updateMember(Member member);
    void deleteMemberByMemberEmail(String memberEmail);
}
