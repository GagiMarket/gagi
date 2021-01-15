package com.gagi.market.item.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
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
    private static final String ITEM_API_URI = "/api/items";

    @LocalServerPort private int port;
    @Autowired private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        itemRepository.deleteAll();
    }

    @Test
    public void list() throws Exception {
        //given
        itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());
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

    @Test
    public void createItem() throws Exception {
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
                .andExpect(status().isCreated());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getItemName()).isEqualTo("m1 맥북 프로");
        assertThat(list.get(0).getItemCategory()).isEqualTo("노트북");
    }

    @Test
    public void updateItem() throws Exception {
        //given
        Item item = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());
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
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().is(201));
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getItemName()).isEqualTo(expectedItemName);
    }

    @Test
    public void deleteItem() throws Exception {
        //given
        Item item = itemRepository.save(Item.builder()
                .itemName("m1 맥북 프로")
                .itemDescription("2021 신형 애플 노트북")
                .itemCategory("노트북")
                .itemPrice(10000)
                .itemLocation("강남역")
                .build());
        //when
        Long itemId = item.getItemId();
        String url = LOCALHOST_URI + port + ITEM_API_URI + "/" + itemId;
        mvc.perform(
                delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        List<Item> list = itemRepository.findAll();
        assertThat(list.size()).isEqualTo(0);
    }
}