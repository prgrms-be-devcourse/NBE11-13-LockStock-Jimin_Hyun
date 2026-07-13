package com.example.lockstock.domain.repository;

import com.example.lockstock.domain.entity.QMember;
import com.example.lockstock.domain.entity.QProduct;
import com.example.lockstock.dto.request.ProductRequestDto;
import com.example.lockstock.dto.response.ProductListItemResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom  {

    private final JPAQueryFactory queryFactory;
    private static final QProduct product = QProduct.product;
    private static final QMember member = QMember.member;

    @Override
    public Page<ProductListItemResponseDto> searchProducts(ProductRequestDto condition, Pageable pageable) {
        List<ProductListItemResponseDto> content = queryFactory.select(
                        Projections.constructor(
                                ProductListItemResponseDto.class,
                                product.id,
                                product.name,
                                product.thumbnailPath,
                                product.price,
                                product.stockQuantity, // 서브쿼리
                                product.version,
                                member.userId
                        )
                )
                .from(product)
                .leftJoin(member).on(product.member.userId.eq(member.userId))
                .where(
                        keywordContains(condition.getKeyword())
                )
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        keywordContains(condition.getKeyword())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    // 상품명 부분 일치 (Like %keyword%). 빈 값이면 조건 없음(null)
    private BooleanExpression keywordContains(String keyword) {
        return (keyword == null || keyword.isBlank()) ? null : product.name.contains(keyword);
    }
}
