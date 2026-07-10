package com.example.lockstock.domain.repository;

import com.example.lockstock.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.imageio.spi.ServiceRegistry;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String attr0);

    Optional<Member> findByUserId(String userId);
}