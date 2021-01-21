package com.gagi.market.item.api.dto;

import com.gagi.market.item.domain.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemPrice;
    private String itemLocation;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.itemCategory = item.getItemCategory();
        this.itemPrice = item.getItemPrice();
        this.itemLocation = item.getItemLocation();
        this.registerDate = item.getRegisterDate();
        this.updateDate = item.getUpdateDate();
    }

    public static ItemResponseDto of(Item item) {
        return new ItemResponseDto(item);
    }
}
