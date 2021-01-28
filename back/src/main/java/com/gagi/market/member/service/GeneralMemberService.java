package com.gagi.market.member.service;

import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class GeneralMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public GeneralMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public Member createMember(Member member) {
        validateDuplicateMember(member.getMemberEmail());
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(String memberEmail) {
        memberRepository.findMemberByMemberEmail(memberEmail)
                .ifPresent(member -> {
                    throw new IllegalArgumentException("이미 등록된 사용자 " + member.getMemberEmail() + " 이(가) 존재합니다.");
                });
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findMemberByMemberEmail(String memberEmail) {
        return memberRepository.findMemberByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    @Transactional
    @Override
    public Member updateMember(String memberEmail, Member member) {
        return findMemberByMemberEmail(memberEmail)
                .update(member);
    }

    @Transactional
    @Override
    public void deleteMemberByMemberEmail(String memberEmail) {
        memberRepository.deleteMemberByMemberEmail(memberEmail);
    }
}
