package com.gagi.market.member.service;

import com.gagi.market.member.api.dto.SessionMember;
import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SessionLoginService implements LoginService {
    private static final String SESSION_MEMBER = "SESSION_MEMBER";
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    public SessionLoginService(HttpSession httpSession, MemberRepository memberRepository) {
        this.httpSession = httpSession;
        this.memberRepository = memberRepository;
    }

    @Override
    public SessionMember login(String memberEmail, String memberPw) {
        SessionMember sessionMember = null;
        Optional<Member> findMember = memberRepository.findMemberByMemberEmailAndMemberPw(memberEmail, memberPw);
        if (findMember.isPresent()) {
            sessionMember = new SessionMember(findMember.get());
            setSessionOfMember(sessionMember);
        }
        return sessionMember;
    }

    private void setSessionOfMember(SessionMember sessionMember) {
        httpSession.setAttribute(SESSION_MEMBER, sessionMember);
    }

    @Override
    public void logout() {
        httpSession.removeAttribute(SESSION_MEMBER);
    }
}
