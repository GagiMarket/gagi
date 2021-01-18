package com.gagi.market.item.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("상품을 생성한다.")
    @Test
    public void createItem() throws Exception {
        //given
        //when
        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());

        //then
        Assertions.assertThat(saveItem.getItemName()).isEqualTo("m1 맥북 프로");
    }

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItemList() throws Exception {
        //given
        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());

        //when
        List<Item> list = itemRepository.findAll();

        //then
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("상품 하나를 삭제한다.")
    @Test
    public void deleteItem() throws Exception {
        //given
        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());

        //when
        Item findItem = itemRepository.findById(saveItem.getItemId()).get();
        itemRepository.delete(findItem);

        //then
        List<Item> list = itemRepository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(0);
    }
}