package com.gagi.market.member.service;

import com.gagi.market.member.api.dto.SessionMember;

public interface LoginService {
    SessionMember login(String memberEmail, String memberPw);
    void logout();
}
