package com.gagi.market.item.service;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Item> findItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    public Page<Item> findItemsByItemNameContains(String itemName, Pageable pageable) {
        return itemRepository.findItemsByItemNameContains(itemName, pageable);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, Item item) {
        Item findItem = findItemById(itemId);
        return findItem.update(item);
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
