package com.pjt.triptravel.attraction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttractionAroundCondition {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Double radiusKm;
}
