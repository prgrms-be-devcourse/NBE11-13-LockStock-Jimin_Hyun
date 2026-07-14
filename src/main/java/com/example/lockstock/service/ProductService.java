package com.example.lockstock.service;

import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.domain.repository.ProductRepository;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.example.lockstock.exception.ProductNotFoundException;
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

    public Product detail(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("상품을 찾을 수 없습니다. id = "+id)
        );
    }
}
