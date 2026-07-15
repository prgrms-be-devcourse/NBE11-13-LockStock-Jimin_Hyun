package com.example.lockstock.service;

import com.example.lockstock.domain.entity.Member;
import com.example.lockstock.domain.entity.Product;
import com.example.lockstock.domain.repository.MemberRepository;
import com.example.lockstock.domain.repository.ProductRepository;
import com.example.lockstock.dto.request.ProductDeleteRequestDto;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.request.ProductUpdateRequestDto;
import com.example.lockstock.dto.request.ProductWriteRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.example.lockstock.exception.MemberNotFoundException;
import com.example.lockstock.exception.ProductNotFoundException;
import com.example.lockstock.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;

    public Page<ProductListItemResponseDto> searchProducts(ProductRequestDto dto, Pageable pageable) {
        return productRepository.searchProducts(dto, pageable);
    }

    public Page<ProductListItemResponseDto> searchAdminProducts(ProductRequestDto dto, Pageable pageable, HttpSession session) {
        return productRepository.searchAdminProducts(dto, pageable, (String) session.getAttribute(SessionConst.USER_ID));
    }

    public Product detail(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("상품을 찾을 수 없습니다. id = "+id)
        );
    }

    @Transactional
    public void saveBoard( ProductWriteRequestDto dto ) {

        String filePath = fileService.storeFile(dto.getFile());

        Member member = memberRepository.findByUserId(dto.getMemberId()).orElseThrow(
                () -> new MemberNotFoundException("상품 등록 과정 중 아이디를 못 찾았습니다!")
        );

        productRepository.save(
                Product.builder()
                        .member(member)
                        .name(dto.getName())
                        .price(dto.getPrice())
                        .contents(dto.getContents())
                        .thumbnailPath(filePath)
                        .stockQuantity(dto.getStockQuantity())
                        .version(0L)
                        .build()
        );
    }

    @Transactional
    public void updateProduct(long id, ProductUpdateRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("수정할 상품 못 찾음! id : " + id)
                );

        String thumbnailPath = product.getThumbnailPath();
        if ( dto.isFileFlag() ) { // 파일 변경이 있었을 경우
            fileService.deleteFile(thumbnailPath); // 기존 파일 삭제
            thumbnailPath = fileService.storeFile(dto.getFile()); // 새 파일 저장
        }

        product.update( dto.getName(), dto.getPrice(), dto.getContents(), thumbnailPath, dto.getStockQuantity());
    }

    @Transactional
    public void deleteProduct(long id, ProductDeleteRequestDto dto) {

        if ( !productRepository.existsById(id) ) {
            throw new ProductNotFoundException("삭제할 상품 못 찾음! id : " + id);
        }
        productRepository.deleteById(id);
        fileService.deleteFile(dto.getThumbnailPath());
    }

}
