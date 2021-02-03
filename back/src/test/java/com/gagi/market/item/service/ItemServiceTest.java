package com.gagi.market.item.service;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void before() {
        memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());
    }

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItems() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();

        Item item = ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity();
        item.createItem(findMember);
        itemRepository.save(item);

        //when
        List<Item> list = itemService.findItems();

        //then
        assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("이름이 포함된 상품 목록을 조회한다.")
    @Test
    public void findItemsByItemNameContains() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();

        Item item1 = ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity();
        item1.createItem(findMember);
        itemRepository.save(item1);

        Item item2 = ItemRequestDto.builder()
                .itemName("에어팟 프로")
                .itemDescription("2021 신형 애플 이어폰")
                .itemCategory("이어폰")
                .itemPrice(30000)
                .itemLocation("강남역")
                .build()
                .toEntity();
        item2.createItem(findMember);
        itemRepository.save(item2);

        //when
        List<Item> findItems = itemService.findItemsByItemNameContains("프로", null).getContent();

        //then
        assertThat(findItems.size()).isEqualTo(2);
    }

    @DisplayName("상품을 생성한다.")
    @Test
    void createItem() {
        //given
        String memberEmail = "test@gagi.com";
        Item item = ItemRequestDto.builder()
                .itemName("에어팟")
                .itemDescription("애플 무선 이어폰")
                .itemCategory("이어폰")
                .itemPrice(20000)
                .itemLocation("가로수길")
                .build()
                .toEntity();
        //when
        Item saveItem = itemService.createItem(item, memberEmail);
        //then
        assertThat(saveItem.getItemName()).isEqualTo("에어팟");
    }

    @DisplayName("상품 정보를 수정한다.")
    @Test
    public void updateItem() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();

        Item item = ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity();
        item.createItem(findMember);
        itemRepository.save(item);

        //when
        Item findItem = itemService.findItemById(item.getItemId());
        Item updateItem = itemService.updateItem(findItem.getItemId(), ItemRequestDto.builder()
                .itemName("m2 맥북 프로")
                .itemDescription("2022 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build()
                .toEntity());

        //then
        assertThat(updateItem.getMember().getMemberId()).isEqualTo(findMember.getMemberId());
        assertThat(updateItem.getItemId()).isEqualTo(findItem.getItemId());
        assertThat(updateItem.getItemName()).isEqualTo("m2 맥북 프로");
        assertThat(updateItem.getItemDescription()).isEqualTo("2022 신형 애플 노트북");
        assertThat(updateItem.getItemCategory()).isEqualTo("노트북");
        assertThat(updateItem.getItemPrice()).isEqualTo(10000);
        assertThat(updateItem.getItemLocation()).isEqualTo("강남역");
    }
}