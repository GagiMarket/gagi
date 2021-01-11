package com.gagi.market.api.dto;

import com.gagi.market.domain.Item;
import lombok.Getter;

@Getter
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemPrice;
    private String itemLocation;

    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.itemCategory = item.getItemCategory();
        this.itemPrice = item.getItemPrice();
        this.itemLocation = item.getItemLocation();
    }

    public static ItemResponseDto of(Item item) {
        return new ItemResponseDto(item);
    }
}
