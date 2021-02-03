package com.gagi.market.item.service;

import com.gagi.market.item.domain.Item;
import com.gagi.market.item.domain.ItemRepository;
import com.gagi.market.member.domain.Member;
import com.gagi.market.member.domain.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public ItemService(ItemRepository itemRepository, MemberRepository memberRepository) {
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
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

    public Item createItem(Item item, String memberEmail) {
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).orElse(null);
        Item createItem = item.createItem(findMember);
        return itemRepository.save(createItem);
    }

    public Item updateItem(Long itemId, Item item) {
        Item findItem = findItemById(itemId);
        return findItem.update(item);
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public boolean checkItemAuthorization(Long itemId, String memberEmail) {
        Member findMember = memberRepository.findMemberByMemberEmail(memberEmail).orElse(null);
        Item findItem = itemRepository.findById(itemId).orElse(null);
        return findItem
                .getMember()
                .getMemberEmail()
                .equals(findMember.getMemberEmail());
    }
}
