package com.example.sushiorderapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sushiorderapi.model.dto.PopularItemDTO;
import com.example.sushiorderapi.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 期間での注文数カウント
    long countByOrderTimeBetween(LocalDateTime start, LocalDateTime end);

    // ステータスでの注文数カウント
    long countByStatus(String status);

    // 期間での総売上計算
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderTime BETWEEN :start AND :end")
    Double sumRevenueBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // 期間での平均注文金額計算
    @Query("SELECT AVG(o.totalAmount) FROM Order o WHERE o.orderTime BETWEEN :start AND :end")
    Double averageOrderValueBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // 期間での人気商品取得
    @Query("""
        SELECT NEW com.example.sushiorderapi.model.dto.PopularItemDTO(
            i.sushiItem.id,
            i.sushiItem.name,
            COUNT(i),
            SUM(i.price * i.quantity))
        FROM OrderItem i
        JOIN i.order o
        WHERE o.orderTime BETWEEN :start AND :end
        GROUP BY i.sushiItem.id, i.sushiItem.name
        ORDER BY COUNT(i) DESC
        """)
    List<PopularItemDTO> findPopularItemsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}