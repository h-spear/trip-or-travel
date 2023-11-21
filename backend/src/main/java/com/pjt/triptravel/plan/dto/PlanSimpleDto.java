package com.pjt.triptravel.plan.dto;

import com.pjt.triptravel.plan.entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PlanSimpleDto {

    private Long planId;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime registrationDate;

    public static PlanSimpleDto of(Plan plan) {
        return PlanSimpleDto.builder()
                .planId(plan.getId())
                .title(plan.getTitle())
                .startDateTime(plan.getStartDateTime())
                .endDateTime(plan.getEndDateTime())
                .registrationDate(plan.getRegistrationDate())
                .build();
    }
}
