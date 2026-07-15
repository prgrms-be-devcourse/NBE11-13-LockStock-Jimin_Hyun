package com.example.lockstock.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProductWriteRequestDto {

    private Long id;
    private String memberId;
    private String name;
    private String contents;
    private int price;
    private int stockQuantity;
    private MultipartFile file; // 새로 올린 파일(교체할 때만 값이 있음)
}
