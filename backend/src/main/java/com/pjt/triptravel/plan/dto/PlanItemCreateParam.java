package com.pjt.triptravel.plan.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanItemCreateParam {

    private Long attractionId;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
