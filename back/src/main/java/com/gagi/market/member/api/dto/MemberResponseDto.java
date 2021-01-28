package com.gagi.market.member.api.dto;

import com.gagi.market.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String memberEmail;
    private String memberPw;
    private String memberPhoneNumber;
    private String memberAddress;

    public MemberResponseDto(Member member) {
        this.memberEmail = member.getMemberEmail();
        this.memberPw = member.getMemberPw();
        this.memberPhoneNumber = member.getMemberPhoneNumber();
        this.memberAddress = member.getMemberAddress();
    }

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member);
    }
}
