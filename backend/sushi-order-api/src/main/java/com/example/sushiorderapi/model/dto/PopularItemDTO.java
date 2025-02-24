package com.example.sushiorderapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularItemDTO {
    private Long itemId;
    private String itemName;
    private Long orderCount;
    private Double totalRevenue;
}