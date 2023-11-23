package com.pjt.triptravel.plan.dto;

import com.pjt.triptravel.common.utils.TimeFormatUtil;
import com.pjt.triptravel.plan.entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PlanSimpleDto {

    private Long planId;
    private String title;
    private String startDateTime;
    private String endDateTime;
    private String registrationDate;
    private String description;

    public static PlanSimpleDto of(Plan plan) {
        return PlanSimpleDto.builder()
                .planId(plan.getId())
                .title(plan.getTitle())
                .startDateTime(TimeFormatUtil.convertDateTime(plan.getStartDateTime()))
                .endDateTime(TimeFormatUtil.convertDateTime(plan.getEndDateTime()))
                .registrationDate(TimeFormatUtil.convertDateTime(plan.getRegistrationDate()))
                .description(plan.getDescription())
                .build();
    }
}
