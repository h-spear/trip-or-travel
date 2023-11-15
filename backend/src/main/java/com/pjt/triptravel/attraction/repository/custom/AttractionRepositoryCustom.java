package com.pjt.triptravel.attraction.repository.custom;

import com.pjt.triptravel.attraction.dto.AttractionAroundResult;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionRepositoryCustom {

    List<AttractionAroundResult> findWithinRoundRange(BigDecimal latitude, BigDecimal longitude, Double radiusKm);
}
