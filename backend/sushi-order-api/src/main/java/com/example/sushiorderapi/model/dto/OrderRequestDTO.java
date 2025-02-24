package com.example.sushiorderapi.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "顧客名は必須です")
    @Size(min = 1, max = 100, message = "顧客名は1〜100文字で入力してください")
    private String customerName;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "電話番号は10桁または11桁の数字で入力してください")
    private String phoneNumber;

    @NotEmpty(message = "注文項目は必須です")
    private List<OrderItemRequestDTO> items;
}