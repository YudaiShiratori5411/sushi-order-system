package com.example.sushiorderapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sushiorderapi.model.entity.SushiItem;

public interface SushiItemRepository extends JpaRepository<SushiItem, Long> {
    // 在庫のある商品の取得
    List<SushiItem> findByAvailableTrue();
    
    // 価格範囲での商品検索
    List<SushiItem> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // 商品名での検索
    List<SushiItem> findByNameContainingIgnoreCase(String name);
    
    // 価格の安い順に商品を取得
    List<SushiItem> findAllByOrderByPriceAsc();
    
    // 在庫切れ商品の取得
    List<SushiItem> findByAvailableFalse();
    
    // 特定の価格以下の商品を取得
    @Query("SELECT s FROM SushiItem s WHERE s.price <= :maxPrice AND s.available = true")
    List<SushiItem> findAvailableItemsUnderPrice(Double maxPrice);
}