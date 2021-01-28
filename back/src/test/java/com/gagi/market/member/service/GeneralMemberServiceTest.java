package com.gagi.market.member.service;

import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class GeneralMemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @DisplayName("회원을 등록한다.")
    @Test
    public void createMember() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        //when
        memberService.createMember(member);

        //then
        Optional<Member> findMember = memberRepository.findMemberByMemberEmail(member.getMemberEmail());
        assertThat(findMember).isNotEmpty();
        assertThat(findMember.get().getMemberEmail()).isEqualTo(member.getMemberEmail());
        assertThat(findMember.get().getMemberPw()).isEqualTo(member.getMemberPw());
        assertThat(findMember.get().getMemberPhoneNumber()).isEqualTo(member.getMemberPhoneNumber());
        assertThat(findMember.get().getMemberAddress()).isEqualTo(member.getMemberAddress());
    }

    @DisplayName("중복회원이 존재하여 회원을 등록에 실패한다.")
    @Test
    public void createMemberFail() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberService.createMember(member);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.createMember(member);
        });
    }

    @DisplayName("회원 Email 로 회원 정보를 조회한다.")
    @Test
    public void findMemberByMemberEmail() throws Exception {
        //given
        String memberEmail = "member1@gagi.com";
        Member member = memberRepository.save(Member.builder()
                .memberEmail(memberEmail)
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build());

        //when
        Member findMember = memberService.findMemberByMemberEmail(memberEmail);

        //then
        assertThat(findMember.getMemberEmail()).isEqualTo(memberEmail);
    }

    @DisplayName("등록된 회원 정보 전체를 조회한다.")
    @Test
    public void findMembers() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build());
        //when
        List<Member> members = memberService.findMembers();

        //then
        assertThat(members.size()).isEqualTo(1);
    }

    @DisplayName("이메일로 회원을 찾고 회원 정보를 수정한다.")
    @Test
    public void updateMember() throws Exception {
        //given
        String memberEmail = "member1@gagi.com";
        Member member = memberRepository.save(Member.builder()
                .memberEmail(memberEmail)
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build());
        //when
        String updatePw = "1234";
        Member updateMember = memberService.updateMember(memberEmail, Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw(updatePw)
                .build());
        //then
        assertThat(updateMember.getMemberEmail()).isEqualTo(member.getMemberEmail());
        assertThat(updateMember.getMemberPw()).isEqualTo(updatePw);
    }

    @DisplayName("회원 Email 로 회원 정보를 삭제한다.")
    @Test
    public void deleteMemberByMemberEmail() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build());
        //when
        memberService.deleteMemberByMemberEmail(member.getMemberEmail());
        //then
        Optional<Member> findMember = memberRepository.findMemberByMemberEmail(member.getMemberEmail());
        assertThat(findMember).isEmpty();
    }
}
