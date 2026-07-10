package com.example.lockstock.domain.repository;

import com.example.lockstock.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}