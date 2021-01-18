package com.gagi.market.item.api.dto;

import com.gagi.market.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequestDto {
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private int itemPrice;
    private String itemLocation;

    @Builder
    public ItemRequestDto(String itemName, String itemDescription, String itemCategory, int itemPrice, String itemLocation) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemLocation = itemLocation;
    }

    public Item toEntity() {
        return Item.builder()
                .itemName(itemName)
                .itemDescription(itemDescription)
                .itemCategory(itemCategory)
                .itemPrice(itemPrice)
                .itemLocation(itemLocation)
                .build();
    }
}
