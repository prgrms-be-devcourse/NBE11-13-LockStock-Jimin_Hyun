package com.example.lockstock.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED) //기본생성자는 필수라서 외부접근 막기용으로 protected
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String userName;

    @OneToMany(mappedBy = "member")
    private List<Product> products = new ArrayList<>();
}