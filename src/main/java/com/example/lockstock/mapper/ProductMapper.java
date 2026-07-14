package com.example.lockstock.mapper;

import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.dto.response.ProductDetailResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductDetailResponse toProductDetailResponseDto(Product product) {

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .thumbnailPath(product.getThumbnailPath())
                .contents(product.getContents())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .version(product.getVersion())
                .memberId(product.getMember().getUserId())
                .build();
    }
}
