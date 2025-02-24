package com.example.sushiorderapi.model.dto;

import lombok.Data;

@Data
public class AdminDashboardResponse {
    private Long totalOrders;        // 注文総数
    private Double totalRevenue;     // 総売上
    private Long pendingOrders;      // 保留中の注文数
    private Long activeUsers;        // アクティブユーザー数
    private Long stockOutItems;      // 在庫切れ商品数
}