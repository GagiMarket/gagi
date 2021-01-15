package com.gagi.market.item.api;

import com.gagi.market.item.api.dto.ItemRequestDto;
import com.gagi.market.item.api.dto.ItemResponseDto;
import com.gagi.market.item.domain.Item;
import com.gagi.market.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.gagi.market.item.api.ItemApiController.ITEM_API_URI;

@RestController
@RequestMapping(ITEM_API_URI)
public class ItemApiController {
    public static final String ITEM_API_URI = "/api/items";

    private final ItemService itemService;

    public ItemApiController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> create(@RequestBody ItemRequestDto requestDto) {
        Item item = itemService.create(requestDto);
        return ResponseEntity
                .created(URI.create(ITEM_API_URI + "/" + item.getItemId()))
                .body(ItemResponseDto.of(item));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> list() {
        List<ItemResponseDto> responses = itemService.list().stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .body(responses);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItemById(@PathVariable Long itemId) {
        ItemResponseDto findItem = ItemResponseDto.of(itemService.findItemById(itemId));
        return ResponseEntity
                .ok()
                .body(findItem);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> update(@PathVariable Long itemId, @RequestBody ItemRequestDto requestDto) {
        Item item = itemService.update(itemId, requestDto);
        return ResponseEntity
                .created(URI.create(ITEM_API_URI + "/" + item.getItemId()))
                .body(ItemResponseDto.of(item));
    }

    @DeleteMapping("/{itemId}")
    public void delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
    }
}
