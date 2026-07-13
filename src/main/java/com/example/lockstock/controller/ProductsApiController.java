package com.example.lockstock.controller;

import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.example.lockstock.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsApiController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductListItemResponseDto> searchProducts( //js에서 Page는 content(<dto>의 필드)를 갖는다.
                                                          @ModelAttribute ProductRequestDto dto,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.searchProducts(dto, pageable);
    }

    /*@GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product); // 여기서 엔티티의 version과 member_id까지 전부 전달됨
        return "productDetail";
    }*/
}
