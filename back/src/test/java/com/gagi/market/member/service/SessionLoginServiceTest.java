package com.gagi.market.member.service;

import com.gagi.market.member.api.dto.SessionMember;
import com.gagi.market.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SessionLoginServiceTest {
    private static final String SESSION_MEMBER = "SESSION_MEMBER";
    @Autowired
    MemberService memberService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    LoginService loginService;

    @DisplayName("로그인에 성공한다.")
    @Test
    public void login_success() throws Exception {
        //given
        String memberEmail = "member1@gagi.com";
        String memberPw = "test";
        Member member = Member.builder()
                .memberEmail(memberEmail)
                .memberPw(memberPw)
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberService.createMember(member);
        //when
        loginService.login(memberEmail, memberPw);
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute(SESSION_MEMBER);
        //then
        assertThat(sessionMember.getMemberEmail()).isEqualTo(memberEmail);
    }

    @DisplayName("회원정보가 존재하지 않아 로그인에 실패한다.")
    @Test
    public void login_fail() throws Exception {
        //given
        String memberEmail = "member1@gagi.com";
        String memberPw = "test";
        Member member = Member.builder()
                .memberEmail(memberEmail)
                .memberPw(memberPw)
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberService.createMember(member);
        //when
        String fakeEmail = "member2@gagi.com";
        loginService.login(fakeEmail, memberPw);
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute(SESSION_MEMBER);
        //then
        assertThat(sessionMember).isNull();
    }

    @DisplayName("로그아웃에 성공한다.")
    @Test
    void logout() throws Exception {
        //given
        String memberEmail = "member1@gagi.com";
        String memberPw = "test";
        Member member = Member.builder()
                .memberEmail(memberEmail)
                .memberPw(memberPw)
                .memberPhoneNumber("010-1234-5678")
                .memberAddress("서울특별시 가지동 가지마켓 2층")
                .build();
        memberService.createMember(member);

        //when
        loginService.login(memberEmail, memberPw);

        //then
        Object sessionMember = httpSession.getAttribute(SESSION_MEMBER);
        assertThat(sessionMember).isNotNull();
        loginService.logout();
        sessionMember = httpSession.getAttribute(SESSION_MEMBER);
        assertThat(sessionMember).isNull();
    }
}
