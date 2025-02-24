package com.example.sushiorderapi.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SushiItemRequestDTO {
    @NotBlank(message = "商品名は必須です")
    @Size(min = 1, max = 100, message = "商品名は1〜100文字で入力してください")
    private String name;

    @Size(max = 500, message = "説明は500文字以内で入力してください")
    private String description;

    @NotNull(message = "価格は必須です")
    @Min(value = 0, message = "価格は0以上を指定してください")
    @Max(value = 100000, message = "価格は100,000円以下を指定してください")
    private Double price;

    @Pattern(regexp = "^(https?://.*|)$", message = "有効なURL形式で入力してください")
    private String imageUrl;

    private Boolean available = true;
}