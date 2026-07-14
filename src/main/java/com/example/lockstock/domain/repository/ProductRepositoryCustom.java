package com.example.lockstock.domain.repository;

import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductListItemResponseDto> searchProducts(ProductRequestDto condition, Pageable pageable);
    Page<ProductListItemResponseDto> searchAdminProducts(ProductRequestDto condition, Pageable pageable, String userId);
}
