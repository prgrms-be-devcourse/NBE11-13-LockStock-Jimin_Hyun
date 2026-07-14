package com.example.lockstock.controller;

import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.domain.repository.ProductRepository;
import com.example.lockstock.dto.request.ProductDeleteRequestDto;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.request.ProductUpdateRequestDto;
import com.example.lockstock.dto.response.ProductDetailResponse;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.example.lockstock.mapper.ProductMapper;
import com.example.lockstock.service.FileService;
import com.example.lockstock.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminApiController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final FileService fileService;

    @GetMapping
    public Page<ProductListItemResponseDto> searchAdminProducts( //js에서 Page는 content(<dto>의 필드)를 갖는다.
                                                            @ModelAttribute ProductRequestDto dto,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "20") int size,
                                                                 HttpSession session
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.searchAdminProducts(dto, pageable , session);
    }

    @GetMapping("/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable long id
    ) {
        Product productDetail = productService.detail(id);
        return productMapper.toProductDetailResponseDto(productDetail);
    }

    /*@PostMapping("/new")
    public void saveProduct(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getFile());
    }*/

    @PutMapping(value = "/{id}")
    public void updateProduct(
            @PathVariable long id,
            @ModelAttribute ProductUpdateRequestDto dto) {
        productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(
            @PathVariable long id,
            @RequestBody ProductDeleteRequestDto dto
    ) {
        productService.deleteProduct(id, dto);
    }

    @DeleteMapping("/file")
    public void deleteFile(
            @RequestParam String thumbnailPath
    ) {
        fileService.deleteFile(thumbnailPath);
    }

}
