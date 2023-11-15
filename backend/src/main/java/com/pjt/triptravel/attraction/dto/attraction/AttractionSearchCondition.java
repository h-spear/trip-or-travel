package com.pjt.triptravel.attraction.dto.attraction;

import lombok.Data;

@Data
public class AttractionSearchCondition {

    private Long sidoCode;
    private Long gugunCode;
    private String keyword;
    private Integer likeCountGe;
    private AttractionSearchOrder order;
}
