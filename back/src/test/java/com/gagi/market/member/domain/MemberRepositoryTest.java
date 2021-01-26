package com.gagi.market.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("사용자를 생성한다.")
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
        Member result = memberRepository.save(member);

        //then
        assertThat(result.getMemberEmail()).isEqualTo(member.getMemberEmail());
        assertThat(result.getMemberPw()).isEqualTo(member.getMemberPw());
        assertThat(result.getMemberPhoneNumber()).isEqualTo(member.getMemberPhoneNumber());
        assertThat(result.getMemberAddress()).isEqualTo(member.getMemberAddress());
    }

    @DisplayName("등록된 사용자 전체를 조회한다.")
    @Test
    public void findMembers() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberRepository.save(member);
        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @DisplayName("사용자 정보를 수정한다.")
    @Test
    public void updateMember() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberRepository.save(member);
        //when
        String updatePw = "test2";
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        Member updateMember = findMember.update(Member.builder()
                .memberPw(updatePw)
                .build());
        //then
        assertThat(updateMember.getMemberPw()).isEqualTo(updatePw);
    }

    @DisplayName("사용자 Id 로 사용자를 삭제한다.")
    @Test
    public void deleteMemberById() throws Exception {
        //given
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberRepository.save(member);
        //when
        memberRepository.deleteById(member.getMemberId());
        Member findMember = memberRepository.findById(member.getMemberId()).orElse(null);
        //then
        assertThat(findMember).isEqualTo(null);
    }

    @DisplayName("사용자 Email 로 사용자를 삭제한다.")
    @Test
    public void deleteMemberByMemberEmail() throws Exception {
        Member member = Member.builder()
                .memberEmail("member1@gagi.com")
                .memberPw("test")
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberRepository.save(member);
        //when
        memberRepository.deleteByMemberEmail(member.getMemberEmail());
        Member findMember = memberRepository.findById(member.getMemberId()).orElse(null);
        //then
        assertThat(findMember).isEqualTo(null);
    }
}
