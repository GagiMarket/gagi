package com.gagi.market.item.domain;

import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
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
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
    }

    @DisplayName("회원은 상품을 등록할 수 있다.")
    @Test
    public void memberCanCreateItem() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());
        //when
        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .member(member)
                .build());
        //then
        assertThat(saveItem.getMember().getMemberEmail()).isEqualTo("test@gagi.com");
        assertThat(saveItem.getItemName()).isEqualTo("m1 맥북 프로");
    }

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItems() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());

        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .member(member)
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
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());

        for (int i = 0; i < 20; i++) {
            itemRepository.save(Item.builder()
                    .itemName("m"+i+" 맥북 프로")
                    .itemDescription("2021 신형 애플 노트북")
                    .itemCategory("노트북")
                    .itemPrice(10000+i)
                    .itemLocation("강남역")
                    .member(member)
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

    @DisplayName("상품에 대한 권한을 가진 회원은 상품 정보를 수정할 수 있다.")
    @Test
    public void memberWithRightsToTheItemCanUpdateItem() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());

        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .member(member)
                .build());

        //when
        Member findMember = memberRepository.findMemberByMemberEmail("test@gagi.com").get();
        Item updateItem = saveItem.update(Item.builder()
                .itemName("m60 맥북 프로")
                .itemDescription("5050 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(1500)
                .itemLocation("강남역")
                .member(findMember)
                .build());
        Item findItem = itemRepository.findById(updateItem.getItemId()).get();

        //then
        assertThat(findItem.getMember().getMemberEmail().equals(findMember.getMemberEmail())).isTrue();
        assertThat(findItem.getItemName()).isEqualTo("m60 맥북 프로");
    }

    @DisplayName("상품에 대한 권한을 가진 회원은 상품을 삭제할 수 있다.")
    @Test
    public void memberWithRightsToTheItemCanDeleteItem() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());

        Item saveItem = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .member(member)
                .build());

        //when
        Member findMember = memberRepository.findMemberByMemberEmail("test@gagi.com").get();
        Item findItem = itemRepository.findById(saveItem.getItemId()).get();
        itemRepository.delete(findItem);

        //then
        List<Item> list = itemRepository.findAll();
        assertThat(findItem.getMember().getMemberEmail().equals(findMember.getMemberEmail())).isTrue();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("이름이 포함된 상품 목록을 조회한다.")
    @Test
    public void findItemListByName() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());

        itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .member(member)
                .build());

        itemRepository.save(Item.builder()
                .itemName("m2 맥북 프로")
                .itemDescription("2022 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(20000)
                .itemLocation("서울역")
                .member(member)
                .build());

        itemRepository.save(Item.builder()
                .itemName("에어팟 프로")
                .itemDescription("2022 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(20000)
                .itemLocation("서울역")
                .member(member)
                .build());

        //when
        List<Item> findItemList = itemRepository.findItemsByItemNameContains("프로", null).getContent();

        //then
        assertThat(findItemList.size()).isEqualTo(3);
    }
}