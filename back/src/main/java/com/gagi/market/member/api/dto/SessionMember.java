package com.gagi.market.member.api.dto;

import com.gagi.market.member.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String memberEmail;
    private String memberPhoneNumber;
    private String memberAddress;

    public SessionMember(Member member) {
        this.memberEmail = member.getMemberEmail();
        this.memberPhoneNumber = member.getMemberPhoneNumber();
        this.memberAddress = member.getMemberAddress();
    }
}
