package com.gagi.market.item.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
    }

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
        assertThat(saveItem.getItemName()).isEqualTo("m1 맥북 프로");
        assertThat(saveItem.getRegisterDate()).isEqualTo(saveItem.getUpdateDate());
    }

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItems() throws Exception {
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
        assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("페이징 조건을 넣어서 상품 목록을 조회한다.")
    @Test
    public void findItemsByPageable() throws Exception {
        //given
        for (int i = 0; i < 20; i++) {
            itemRepository.save(Item.builder()
                    .itemName("m"+i+" 맥북 프로")
                    .itemDescription("2021 신형 애플 노트북")
                    .itemCategory("노트북")
                    .itemPrice(10000+i)
                    .itemLocation("강남역")
                    .build());
        }
        //when
        Page<Item> findItems = itemRepository.findAll(PageRequest.of(1, 5));

        //then
        assertThat(findItems.getNumber()).isEqualTo(1);//현재 페이지 번호
        assertThat(findItems.getSize()).isEqualTo(5);//현재 조회된 데이터 개수
        assertThat(findItems.getTotalElements()).isEqualTo(20);//전체 데이터 개수
        assertThat(findItems.getTotalPages()).isEqualTo(4);//전체 페이지 개수
        assertThat(findItems.isFirst()).isFalse();//현재 첫 번째 페이지 인가?
        assertThat(findItems.hasNext()).isTrue();//다음 페이지 유무
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
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("이름이 포함된 상품 목록을 조회한다.")
    @Test
    public void findItemListByName() throws Exception {
        //given
        itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());

        itemRepository.save(Item.builder()
                .itemName("m2 맥북 프로")
                .itemDescription("2022 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(20000)
                .itemLocation("서울역")
                .build());

        itemRepository.save(Item.builder()
                .itemName("에어팟 프로")
                .itemDescription("2022 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(20000)
                .itemLocation("서울역")
                .build());

        //when
        List<Item> findItemList = itemRepository.findItemsByItemNameContains("프로");

        //then
        assertThat(findItemList.size()).isEqualTo(3);
    }
}