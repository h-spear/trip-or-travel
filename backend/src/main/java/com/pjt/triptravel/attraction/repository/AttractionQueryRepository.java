package com.pjt.triptravel.attraction.repository;

import com.pjt.triptravel.attraction.dto.attraction.AttractionSearchCondition;
import com.pjt.triptravel.attraction.dto.attraction.AttractionSearchOrder;
import com.pjt.triptravel.attraction.dto.attraction.AttractionSearchResult;
import com.pjt.triptravel.attraction.dto.QAttractionSearchResult;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.pjt.triptravel.attraction.entity.QAttractionInfo.attractionInfo;

@Slf4j
@Repository
public class AttractionQueryRepository {

    private static final double radius = 6371; // 지구 반지름(km)
    private static final BigDecimal toRadian = new BigDecimal(Math.PI / 180);

    private final JPAQueryFactory query;

    public AttractionQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Slice<AttractionSearchResult> query(AttractionSearchCondition condition, Pageable pageable) {
        List<AttractionSearchResult> result = query.select(new QAttractionSearchResult(
                    attractionInfo.id,
                    attractionInfo.title,
                    attractionInfo.addr1,
                    attractionInfo.addr2,
                    attractionInfo.zipcode,
                    attractionInfo.tel,
                    attractionInfo.imageUrl,
                    attractionInfo.imageUrl2,
                    attractionInfo.sido.code,
                    attractionInfo.gugun.code,
                    attractionInfo.latitude,
                    attractionInfo.longitude,
                    attractionInfo.mlevel,
                    attractionInfo.readCount,
                    attractionInfo.likeCount))
                .from(attractionInfo)
                .where(titleContains(condition.getKeyword()),
                    codeMatch(attractionInfo.sido.code, condition.getSidoCode()),
                    codeMatch(attractionInfo.gugun.code, condition.getGugunCode()),
                    likeCountGe(condition.getLikeCountGe()))
                .orderBy(getOrderSpecifier(condition.getOrder()), attractionInfo.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(result, pageable, hasNext);
    }

    private BooleanExpression codeMatch(NumberExpression<Long> code, Long conditionCode) {
        return conditionCode != null ? code.eq(conditionCode) : null;
    }

    private BooleanExpression titleContains(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        log.info("title condition={}", keyword.toLowerCase());
        return attractionInfo.title.lower().contains(keyword.toLowerCase());
    }

    private BooleanExpression likeCountGe(Integer likeCountGeCond) {
        return likeCountGeCond != null ? attractionInfo.likeCount.goe(likeCountGeCond) : null;
    }

    private OrderSpecifier<?> getOrderSpecifier(AttractionSearchOrder order) {
        if (order == AttractionSearchOrder.LIKE_COUNT_DESC) {
            return attractionInfo.likeCount.desc();
        } else if (order == AttractionSearchOrder.READ_COUNT_DESC) {
            return attractionInfo.readCount.desc();
        } else {
            return attractionInfo.id.asc();
        }
    }
}
