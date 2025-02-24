package com.example.sushiorderapi.model.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class OrderStatistics {
    private Long totalOrders;            // 期間内の注文総数
    private Double totalRevenue;         // 期間内の総売上
    private Double averageOrderValue;    // 平均注文金額
    private List<PopularItemDTO> popularItems;  // 人気商品リスト
    private Map<String, Long> ordersByStatus;   // ステータス別注文数
}