package com.gagi.market.member.api.dto;

import com.gagi.market.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {
    @NonNull
    private String memberEmail;
    @NonNull
    private String memberPw;

    @Builder
    public MemberLoginRequestDto(String memberEmail, String memberPw) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
    }

    public Member toEntity() {
        return Member.builder()
                .memberEmail(memberEmail)
                .memberPw(memberPw)
                .build();
    }
}
