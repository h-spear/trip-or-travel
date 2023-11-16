package com.pjt.triptravel.attraction.dto.attraction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AttractionAroundResult {

    private Long id;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String imageUrl;
    private String imageUrl2;
    private Long sidoCode;
    private Long gugunCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mlevel;
    private int readCount;
    private int likeCount;
    private double distance;
    private String cat1;
    private String cat2;
    private String cat3;
}
