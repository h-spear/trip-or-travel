package com.pjt.triptravel.attraction.dto;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AttractionSimpleDto {

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

    public static AttractionSimpleDto of(AttractionInfo attractionInfo) {
        return AttractionSimpleDto.builder()
                .id(attractionInfo.getId())
                .title(attractionInfo.getTitle())
                .addr1(attractionInfo.getAddr1())
                .addr2(attractionInfo.getAddr2())
                .zipcode(attractionInfo.getZipcode())
                .tel(attractionInfo.getTel())
                .imageUrl(attractionInfo.getImageUrl())
                .imageUrl2(attractionInfo.getImageUrl2())
                .sidoCode(attractionInfo.getSido().getCode())
                .gugunCode(attractionInfo.getGugun().getCode())
                .latitude(attractionInfo.getLatitude())
                .longitude(attractionInfo.getLongitude())
                .mlevel(attractionInfo.getMlevel())
                .readCount(attractionInfo.getReadCount())
                .likeCount(attractionInfo.getLikeCount())
                .build();
    }
}
