package com.gagi.market.item.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Builder
    public Item(String itemName, String itemDescription, String itemCategory, int itemPrice, String itemLocation) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemLocation = itemLocation;
    }

    //==비즈니스 로직==//
    public Item update(Item item) {
        this.itemName = item.itemName;
        this.itemDescription = item.itemDescription;
        this.itemCategory = item.itemCategory;
        this.itemPrice = item.itemPrice;
        this.itemLocation = item.itemLocation;
        return this;
    }
}
