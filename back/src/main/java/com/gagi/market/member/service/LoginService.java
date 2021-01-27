package com.gagi.market.member.service;

public interface LoginService {
    void login(String memberEmail, String memberPw);
    void logout();
}
