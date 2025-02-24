package com.example.sushiorderapi.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MenuCategoryRequest {
    @NotBlank(message = "カテゴリー名は必須です")
    @Size(min = 1, max = 50, message = "カテゴリー名は1〜50文字で入力してください")
    private String name;

    @Size(max = 200, message = "説明は200文字以内で入力してください")
    private String description;

    @NotNull(message = "表示順は必須です")
    @Min(value = 0, message = "表示順は0以上を指定してください")
    private Integer displayOrder;
}