package com.example.sushiorderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sushiorderapi.model.dto.MenuCategoryRequest;
import com.example.sushiorderapi.service.AdminMenuService;

@RestController
@RequestMapping("/api/admin/menu")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminMenuController {

    @Autowired
    private AdminMenuService adminMenuService;

    // メニューカテゴリーの作成
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody MenuCategoryRequest request) {
        return ResponseEntity.ok(adminMenuService.createCategory(request));
    }

    // 商品画像のアップロード
    @PostMapping("/items/{itemId}/image")
    public ResponseEntity<?> uploadItemImage(
            @PathVariable Long itemId,
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(adminMenuService.uploadItemImage(itemId, image));
    }

    // 商品の一括価格更新
    @PatchMapping("/items/bulk-price-update")
    public ResponseEntity<?> bulkUpdatePrices(
            @RequestParam List<Long> itemIds,
            @RequestParam Double priceChangePercentage) {
        adminMenuService.bulkUpdatePrices(itemIds, priceChangePercentage);
        return ResponseEntity.ok().build();
    }

    // 在庫状況の一括更新
    @PatchMapping("/items/bulk-availability-update")
    public ResponseEntity<?> bulkUpdateAvailability(
            @RequestParam List<Long> itemIds,
            @RequestParam Boolean available) {
        adminMenuService.bulkUpdateAvailability(itemIds, available);
        return ResponseEntity.ok().build();
    }
}