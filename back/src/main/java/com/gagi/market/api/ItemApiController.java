package com.gagi.market.api;

import com.gagi.market.api.dto.ItemRequestDto;
import com.gagi.market.api.dto.ItemResponseDto;
import com.gagi.market.domain.Item;
import com.gagi.market.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.gagi.market.api.ItemApiController.*;

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
