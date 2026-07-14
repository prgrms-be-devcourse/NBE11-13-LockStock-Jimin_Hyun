package com.example.lockstock.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "userId") // DB 컬럼 member_id가 Member테이블의 user_id를 참조함
    private Member member;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(length = 255)
    private String thumbnailPath;

    @Column(nullable = false)
    private int stockQuantity;

    @Version
    private Long version;

    public void update(String name, int price, String contents, String thumbnailPath, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.contents = contents;
        this.thumbnailPath = thumbnailPath;
        this.stockQuantity = stockQuantity;
    }
}