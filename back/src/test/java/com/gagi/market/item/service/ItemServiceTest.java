package com.gagi.market.item.service;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItemList() throws Exception {
        //given
        itemRepository.save(ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity());

        //when
        List<Item> list = itemService.findItemList();

        //then
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("상품을 생성한다.")
    @Test
    void createItem() {
        Item item = itemService.createItem(ItemRequestDto.builder()
                .itemName("에어팟")
                .itemDescription("애플 무선 이어폰")
                .itemCategory("이어폰")
                .itemPrice(20000)
                .itemLocation("가로수길")
                .build());
        Assertions.assertThat(item.getItemName()).isEqualTo("에어팟");
    }

    @DisplayName("상품 정보를 수정한다.")
    @Test
    public void updateItem() throws Exception {
        //given
        Item item = itemRepository.save(ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity());

        //when
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .itemName("m2 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        Item findItem = itemService.findItemById(item.getItemId());
        itemService.updateItem(findItem.getItemId(), requestDto);

        //then
        Assertions.assertThat(findItem.getItemName()).isEqualTo("m2 맥북 프로");
    }
}