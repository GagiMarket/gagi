package com.gagi.market.member.service;

import com.gagi.market.member.domain.Member;

import java.util.List;

public interface MemberService {
    Member createMember(Member member);
    List<Member> findMembers();
    Member findMemberByMemberEmail(String memberEmail);
    Member updateMember(String memberEmail, Member member);
    void deleteMemberByMemberEmail(String memberEmail);
}
