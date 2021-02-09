package com.gagi.market.item.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import com.gagi.market.member.api.dto.SessionMember;
import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemApiControllerTest {
    private static final String LOCALHOST_URI = "http://localhost:";
    private static final String ITEM_API_URI = "/api/v1.0/items";

    @LocalServerPort private int port;
    @Autowired private WebApplicationContext context;
    private MockMvc mvc;
    protected MockHttpSession session;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        Member member = memberRepository.save(Member.builder()
                .memberEmail("test@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());
        session = new MockHttpSession();
        session.setAttribute("SESSION_MEMBER", new SessionMember(member));
    }

    @AfterEach
    public void tearDown() {
        itemRepository.deleteAll();
        session.clearAttributes();
    }

    @DisplayName("등록된 상품 목록을 조회한다.")
    @Test
    public void findItems() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();
        Item item = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item.createItem(findMember);
        itemRepository.save(item);

        //when
        String url = LOCALHOST_URI + port + ITEM_API_URI;
        mvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("이름이 포함된 상품 목록을 조회한다.")
    @Test
    public void findItemsByItemNameContains() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();
        Item item1 = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item1.createItem(findMember);
        itemRepository.save(item1);

        Item item2 = Item.builder()
                .itemName("에어팟 프로")
                .itemDescription("2021 신형 애플 이어폰")
                .itemCategory("이어폰")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item2.createItem(findMember);
        itemRepository.save(item2);

        //when
        String searchField = "itemName";
        String searchName = "프로";
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/search?" + searchField + "=" + searchName;
        mvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        List<Item> findItems = itemRepository.findItemsByItemNameContains(searchName, null).getContent();
        assertThat(findItems.size()).isEqualTo(2);
    }

    @DisplayName("회원은 상품 등록을 성공한다.")
    @Test
    public void memberSuccessInCreateItem() throws Exception {
        //given
        String url = LOCALHOST_URI + port + ITEM_API_URI;
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        //when
        mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getItemName()).isEqualTo("m1 맥북 프로");
        assertThat(list.get(0).getItemCategory()).isEqualTo("노트북");
    }

    @DisplayName("비회원은 상품 등록을 실패한다.")
    @Test
    public void nonMemberFailInCreateItem() throws Exception {
        //given
        String url = LOCALHOST_URI + port + ITEM_API_URI;
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        //when
        mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());
        //then
    }

    @DisplayName("권한을 가진 회원은 상품 정보 수정을 성공한다.")
    @Test
    public void permittedMemberSuccessToUpdateItem() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();
        Item item = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item.createItem(findMember);
        itemRepository.save(item);

        //when
        Long itemId = item.getItemId();
        String expectedItemName = "m1 맥북 에어";
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .itemName(expectedItemName)
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/" + itemId;
        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getItemName()).isEqualTo(expectedItemName);
    }

    @DisplayName("상품에 권한이 없는 회원은 상품 정보 수정을 실패한다.")
    @Test
    public void noPermittedMemberFailToUpdateItem() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("fake@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());
        Item item = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item.createItem(member);
        itemRepository.save(item);

        //when
        Long itemId = item.getItemId();
        String expectedItemName = "m1 맥북 에어";
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .itemName(expectedItemName)
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/" + itemId;
        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());
        //then
    }

    @DisplayName("권한을 가진 회원은 상품 삭제를 성공한다.")
    @Test
    public void deleteItem() throws Exception {
        //given
        String memberEmail = "test@gagi.com";
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).get();
        Item item = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item.createItem(findMember);
        itemRepository.save(item);
        //when
        Long itemId = item.getItemId();
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/" + itemId;
        mvc.perform(
                delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("상품에 권한이 없는 회원은 상품 삭제를 실패한다.")
    @Test
    public void noPermittedMemberFailToDeleteItem() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .memberEmail("fake@gagi.com")
                .memberPw("test")
                .memberAddress("가지특별시 가지동")
                .memberPhoneNumber("010-1234-5678")
                .build());
        Item item = Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build();
        item.createItem(member);
        itemRepository.save(item);
        //when
        Long itemId = item.getItemId();
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/" + itemId;
        mvc.perform(
                delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isUnauthorized());
        //then
    }
}
