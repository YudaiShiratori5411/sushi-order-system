package com.example.sushiorderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sushiorderapi.model.entity.SushiItem;
import com.example.sushiorderapi.service.SushiItemService;

@RestController
@RequestMapping("/api/sushi-items")
@CrossOrigin(origins = "*")
public class SushiItemController {

    @Autowired
    private SushiItemService sushiItemService;

    // 全商品の取得
    @GetMapping
    public ResponseEntity<List<SushiItem>> getAllItems() {
        List<SushiItem> items = sushiItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // 在庫のある商品のみ取得
    @GetMapping("/available")
    public ResponseEntity<List<SushiItem>> getAvailableItems() {
        List<SushiItem> items = sushiItemService.getAvailableItems();
        return ResponseEntity.ok(items);
    }

    // 商品の新規追加（管理者用）
    @PostMapping
    public ResponseEntity<SushiItem> createItem(@RequestBody SushiItem item) {
        SushiItem createdItem = sushiItemService.createItem(item);
        return ResponseEntity.ok(createdItem);
    }

    // 商品の更新（管理者用）
    @PutMapping("/{id}")
    public ResponseEntity<SushiItem> updateItem(
            @PathVariable Long id,
            @RequestBody SushiItem item) {
        SushiItem updatedItem = sushiItemService.updateItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }

    // 商品の在庫状態の更新（管理者用）
    @PatchMapping("/{id}/availability")
    public ResponseEntity<SushiItem> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        SushiItem updatedItem = sushiItemService.updateAvailability(id, available);
        return ResponseEntity.ok(updatedItem);
    }
}