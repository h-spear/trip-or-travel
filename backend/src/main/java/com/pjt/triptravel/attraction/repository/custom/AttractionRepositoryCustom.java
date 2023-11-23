package com.pjt.triptravel.attraction.repository.custom;

import java.math.BigDecimal;
import java.util.List;

import com.pjt.triptravel.attraction.dto.attraction.AttractionAroundResult;
import com.pjt.triptravel.attraction.dto.attraction.AttractionSearchResult;

public interface AttractionRepositoryCustom {

    List<AttractionAroundResult> findWithinRoundRange(BigDecimal latitude, BigDecimal longitude, Double radiusKm);

    List<AttractionSearchResult> findTopRatingByContentType(int top);
}
