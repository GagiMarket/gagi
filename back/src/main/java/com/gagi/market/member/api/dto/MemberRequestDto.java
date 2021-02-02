package com.gagi.market.member.api.dto;

import com.gagi.market.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String memberEmail;
    private String memberPw;
    private String memberPhoneNumber;
    private String memberAddress;

    @Builder
    public MemberRequestDto(String memberEmail, String memberPw, String memberPhoneNumber, String memberAddress) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberAddress = memberAddress;
    }

    public Member toEntity() {
        return Member.builder()
                .memberEmail(memberEmail)
                .memberPw(memberPw)
                .memberPhoneNumber(memberPhoneNumber)
                .memberAddress(memberAddress)
                .build();
    }
}
