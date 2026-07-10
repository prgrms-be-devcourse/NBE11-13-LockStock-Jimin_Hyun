package com.example.lockstock.domain.repository;

import com.example.lockstock.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}