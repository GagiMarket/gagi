package com.gagi.market.service;

import com.gagi.market.api.dto.ItemRequestDto;
import com.gagi.market.domain.Item;
import com.gagi.market.domain.ItemRepository;
import org.assertj.core.api.Assertions;
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


    @Test
    public void list() throws Exception {
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
        List<Item> list = itemService.list();

        //then
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void create() {
        Item item = itemService.create(ItemRequestDto.builder()
                .itemName("에어팟")
                .itemDescription("애플 무선 이어폰")
                .itemCategory("이어폰")
                .itemPrice(20000)
                .itemLocation("가로수길")
                .build());
        Assertions.assertThat(item.getItemName()).isEqualTo("에어팟");
    }

    @Test
    public void update() throws Exception {
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
        itemService.update(findItem.getItemId(), requestDto);

        //then
        Assertions.assertThat(findItem.getItemName()).isEqualTo("m2 맥북 프로");
    }
}