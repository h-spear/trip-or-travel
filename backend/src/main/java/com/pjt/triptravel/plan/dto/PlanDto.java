package com.pjt.triptravel.plan.dto;

import com.pjt.triptravel.common.utils.TimeFormatUtil;
import com.pjt.triptravel.plan.entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlanDto {

    private Long id;
    private String title;
    private String description;
    private String startDateTime;
    private String endDateTime;
    private List<PlanItemDto> planItems;
    private String registrationDate;

    public static PlanDto of(Plan plan) {
        List<PlanItemDto> planItems = plan.getPlanItems().stream()
                .map(PlanItemDto::of)
                .sorted((Comparator.comparingInt(PlanItemDto::getOrder)))
                .collect(Collectors.toList());

        return PlanDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .startDateTime(TimeFormatUtil.convertDateTime(plan.getStartDateTime()))
                .endDateTime(TimeFormatUtil.convertDateTime(plan.getEndDateTime()))
                .planItems(planItems)
                .registrationDate(TimeFormatUtil.convertDateTime(plan.getRegistrationDate()))
                .build();
    }
}
