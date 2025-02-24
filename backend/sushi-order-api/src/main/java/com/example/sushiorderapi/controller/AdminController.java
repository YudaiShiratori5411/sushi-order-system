package com.example.sushiorderapi.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sushiorderapi.model.dto.AdminDashboardResponse;
import com.example.sushiorderapi.model.dto.OrderStatistics;
import com.example.sushiorderapi.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ダッシュボード情報の取得
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardInfo() {
        return ResponseEntity.ok(adminService.getDashboardInfo());
    }

    // 売上統計の取得
    @GetMapping("/statistics")
    public ResponseEntity<OrderStatistics> getOrderStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(adminService.getOrderStatistics(startDate, endDate));
    }

    // 全ユーザー一覧の取得
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllUsers(page, size));
    }

    // ユーザーの有効/無効化
    @PatchMapping("/users/{userId}/status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.toggleUserStatus(userId));
    }

    // 注文ステータスの一括更新
    @PatchMapping("/orders/bulk-status-update")
    public ResponseEntity<?> bulkUpdateOrderStatus(
            @RequestParam List<Long> orderIds,
            @RequestParam String status) {
        adminService.bulkUpdateOrderStatus(orderIds, status);
        return ResponseEntity.ok().build();
    }
}