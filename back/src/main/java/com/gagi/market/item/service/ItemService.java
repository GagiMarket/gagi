package com.gagi.market.item.service;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    public List<Item> findItemsByItemNameContains(String itemName) {
        return itemRepository.findItemsByItemNameContains(itemName);
    }

    public Item createItem(ItemRequestDto requestDto) {
        return itemRepository.save(requestDto.toEntity());
    }

    public Item updateItem(Long itemId, ItemRequestDto requestDto) {
        Item findItem = findItemById(itemId);
        return findItem.update(requestDto.toEntity());
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
