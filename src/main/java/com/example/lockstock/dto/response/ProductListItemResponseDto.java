package com.example.lockstock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductListItemResponseDto {
    private Long id;
    private String name;
    private String thumbnailPath;
    private int price;
    private int stockQuantity;
    private Long version;
    private String memberId; // 참조하고 있는 user_id
}
