package com.example.lockstock.service;

import com.example.lockstock.domain.repository.ProductRepository;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;

    public Page<ProductListItemResponseDto> searchProducts(ProductRequestDto dto, Pageable pageable) {
        return productRepository.searchProducts(dto, pageable);
    }
}
