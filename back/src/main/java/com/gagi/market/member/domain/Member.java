package com.gagi.market.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberEmail;
    private String memberPw;
    private String memberPhoneNumber;
    private String memberAddress;
    private LocalDateTime registerDate;

    @Builder
    public Member(String memberEmail, String memberPw, String memberPhoneNumber, String memberAddress) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberAddress = memberAddress;
        this.registerDate = LocalDateTime.now();
    }

    public Member update(Member member) {
        this.memberEmail = member.getMemberEmail();
        this.memberPw = member.getMemberPw();
        this.memberPhoneNumber = member.getMemberPhoneNumber();
        this.memberAddress = member.getMemberAddress();
        return this;
    }
}
