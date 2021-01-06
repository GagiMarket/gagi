package com.gagi.market.service;

import com.gagi.market.domain.Item;
import com.gagi.market.domain.ItemRepository;
import javassist.NotFoundException;
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

    public List<Item> list() {
        return itemRepository.findAll();
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        Item findItem = findItemById(item.getItemId());
        return findItem.update(item);
    }

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }
}
