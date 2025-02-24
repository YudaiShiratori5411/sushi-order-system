package com.example.sushiorderapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sushiorderapi.model.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 注文IDによる商品詳細の取得
    List<OrderItem> findByOrderId(Long orderId);
    
    // 特定の商品の注文数集計
    @Query("SELECT oi.sushiItem.id, SUM(oi.quantity) " +
           "FROM OrderItem oi " +
           "GROUP BY oi.sushiItem.id")
    List<Object[]> countOrdersByItem();
}