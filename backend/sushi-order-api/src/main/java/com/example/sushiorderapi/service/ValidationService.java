package com.example.sushiorderapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sushiorderapi.model.dto.OrderRequestDTO;
import com.example.sushiorderapi.model.entity.SushiItem;
import com.example.sushiorderapi.model.exception.ValidationException;
import com.example.sushiorderapi.repository.SushiItemRepository;

@Service
public class ValidationService {
    
    @Autowired
    private SushiItemRepository sushiItemRepository;

    public void validateOrder(OrderRequestDTO orderRequest) {
        List<String> errors = new ArrayList<>();

        // 営業時間のチェック
        if (!isWithinBusinessHours()) {
            errors.add("現在は営業時間外です");
        }

        // 注文項目の存在チェック
        orderRequest.getItems().forEach(item -> {
            SushiItem sushiItem = sushiItemRepository.findById(item.getSushiItemId())
                .orElse(null);
            
            if (sushiItem == null) {
                errors.add("商品ID: " + item.getSushiItemId() + " は存在しません");
            } else if (!sushiItem.getAvailable()) {
                errors.add("商品: " + sushiItem.getName() + " は現在ご注文いただけません");
            }
        });

        // 合計注文数のチェック
        int totalItems = orderRequest.getItems().stream()
            .mapToInt(item -> item.getQuantity())
            .sum();
        if (totalItems > 50) {
            errors.add("1回の注文で最大50個までご注文いただけます");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private boolean isWithinBusinessHours() {
        // 営業時間チェックのロジックを実装
        // 例: 11:00-21:00
        return true; // 実際の実装ではちゃんとチェック
    }
}