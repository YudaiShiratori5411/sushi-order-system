package com.example.sushiorderapi.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {
    @NotNull(message = "商品IDは必須です")
    private Long sushiItemId;

    @Min(value = 1, message = "数量は1以上を指定してください")
    @Max(value = 20, message = "1回の注文で最大20個までです")
    private Integer quantity;
}