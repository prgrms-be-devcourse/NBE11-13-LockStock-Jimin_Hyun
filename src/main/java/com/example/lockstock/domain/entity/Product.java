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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(length = 255)
    private String contents;

    @Column(length = 255)
    private String thumbnailPath;

    @Column(nullable = false)
    private int stockQuantity;

    @Version
    private Long version;
}