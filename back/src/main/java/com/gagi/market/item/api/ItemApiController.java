package com.gagi.market.item.api;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.api.dto.ItemResponseDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.gagi.market.item.api.ItemApiController.ITEM_API_URI;

@RestController
@RequestMapping(ITEM_API_URI)
public class ItemApiController {
    public static final String ITEM_API_URI = "/api/v1.0/items";

    private final ItemService itemService;

    public ItemApiController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDto>> findItems(Pageable pageable) {
        Page<ItemResponseDto> findItems = itemService.findItems(pageable)
                .map(ItemResponseDto::new);
        return ResponseEntity
                .ok()
                .body(findItems);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItemById(@PathVariable Long itemId) {
        ItemResponseDto findItem = ItemResponseDto.of(itemService.findItemById(itemId));
        return ResponseEntity
                .ok()
                .body(findItem);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemResponseDto>> findItemsByItemNameContains(@RequestParam("itemName") String itemName, Pageable pageable) {
        Page<ItemResponseDto> findItems = itemService.findItemsByItemNameContains(itemName, pageable)
                .map(ItemResponseDto::new);
        return ResponseEntity
                .ok()
                .body(findItems);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto requestDto) {
        Item item = itemService.createItem(requestDto.toEntity());
        return ResponseEntity
                .created(URI.create(ITEM_API_URI + "/" + item.getItemId()))
                .body(ItemResponseDto.of(item));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemRequestDto requestDto) {
        Item item = itemService.updateItem(itemId, requestDto.toEntity());
        return ResponseEntity
                .created(URI.create(ITEM_API_URI + "/" + item.getItemId()))
                .body(ItemResponseDto.of(item));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity
                .ok()
                .build();
    }
}
