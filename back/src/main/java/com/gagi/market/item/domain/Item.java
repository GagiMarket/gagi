package com.gagi.market.item.domain;

import com.gagi.market.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemPrice;
    private String itemLocation;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Item(String itemName, String itemDescription, String itemCategory, int itemPrice, String itemLocation, Member member) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemLocation = itemLocation;
        this.registerDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        setMember(member);
    }

    //==연관관계 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getItems().add(this);
    }

    //==비즈니스 로직==//
    public Item update(Item item) {
        this.itemName = item.itemName;
        this.itemDescription = item.itemDescription;
        this.itemCategory = item.itemCategory;
        this.itemPrice = item.itemPrice;
        this.itemLocation = item.itemLocation;
        this.updateDate = LocalDateTime.now();
        return this;
    }
}
