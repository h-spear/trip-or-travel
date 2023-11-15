package com.pjt.triptravel.attraction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttractionSearchCondition {

    private Long sidoCode;
    private Long gugunCode;
    private String keyword;
    private Integer likeCountGe;
    private AttractionSearchOrder order;
}
