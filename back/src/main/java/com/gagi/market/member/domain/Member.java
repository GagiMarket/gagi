package com.gagi.market.member.domain;

import com.gagi.market.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    private String memberEmail;
    private String memberPw;
    private String memberPhoneNumber;
    private String memberAddress;

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();
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
