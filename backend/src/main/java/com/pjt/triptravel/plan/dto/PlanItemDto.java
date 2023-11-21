package com.pjt.triptravel.plan.dto;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.plan.entity.PlanItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PlanItemDto {

    private Long id;
    private Long attractionId;
    private String attractionImageUrl;
    private String attractionImageUrl2;
    private String attractionAddr1;
    private String attractionAddr2;
    private String attractionContentType;
    private Long attractionSidoCode;
    private Long attractionGugunCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int order;

    public static PlanItemDto of(PlanItem planItem) {
        AttractionInfo attraction = planItem.getAttraction();
        return PlanItemDto.builder()
                .id(planItem.getId())
                .attractionId(attraction.getId())
                .attractionImageUrl(attraction.getImageUrl())
                .attractionImageUrl2(attraction.getImageUrl2())
                .attractionAddr1(attraction.getAddr1())
                .attractionAddr2(attraction.getAddr2())
                .attractionGugunCode(attraction.getGugunCode())
                .attractionSidoCode(attraction.getSidoCode())
                .attractionContentType(attraction.getContentType().getName())
                .latitude(attraction.getLatitude())
                .longitude(attraction.getLongitude())
                .memo(planItem.getMemo())
                .startDateTime(planItem.getStartDateTime())
                .endDateTime(planItem.getEndDateTime())
                .order(planItem.getOrder())
                .build();
    }
}
