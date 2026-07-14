package com.example.lockstock.controller;

import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductDetailResponse;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.example.lockstock.mapper.ProductMapper;
import com.example.lockstock.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsApiController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public Page<ProductListItemResponseDto> searchProducts( //js에서 Page는 content(<dto>의 필드)를 갖는다.
                                                          @ModelAttribute ProductRequestDto dto,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.searchProducts(dto, pageable);
    }

    @GetMapping("/detail/{id}")
    public ProductDetailResponse detail(@PathVariable Long id) {
        Product product = productService.detail(id);
        return productMapper.toProductDetailResponseDto(product);
    }
}
