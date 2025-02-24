package com.example.sushiorderapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.sushiorderapi.model.dto.MenuCategoryRequest;
import com.example.sushiorderapi.model.entity.MenuCategory;
import com.example.sushiorderapi.model.entity.SushiItem;
import com.example.sushiorderapi.repository.MenuCategoryRepository;
import com.example.sushiorderapi.repository.SushiItemRepository;

@Service
public class AdminMenuService {

    @Autowired
    private SushiItemRepository sushiItemRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Transactional
    public MenuCategory createCategory(MenuCategoryRequest request) {
        if (menuCategoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("同じ名前のカテゴリーが既に存在します");
        }

        MenuCategory category = new MenuCategory();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setDisplayOrder(request.getDisplayOrder());
        
        return menuCategoryRepository.save(category);
    }

    @Transactional
    public void bulkUpdatePrices(List<Long> itemIds, Double priceChangePercentage) {
        List<SushiItem> items = sushiItemRepository.findAllById(itemIds);
        items.forEach(item -> {
            Double newPrice = item.getPrice() * (1 + priceChangePercentage / 100);
            item.setPrice(Math.round(newPrice * 100.0) / 100.0);
        });
        sushiItemRepository.saveAll(items);
    }

    @Transactional
    public void bulkUpdateAvailability(List<Long> itemIds, Boolean available) {
        List<SushiItem> items = sushiItemRepository.findAllById(itemIds);
        items.forEach(item -> item.setAvailable(available));
        sushiItemRepository.saveAll(items);
    }

    public String uploadItemImage(Long itemId, MultipartFile image) {
        // 画像アップロード処理の実装
        return "uploaded_image_url";
    }
}