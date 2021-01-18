package com.gagi.market.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemsByItemNameContains(String itemName);
}
