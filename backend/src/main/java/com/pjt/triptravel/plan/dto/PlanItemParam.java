package com.pjt.triptravel.plan.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanItemParam {

    private Long attractionId;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int order;
}
