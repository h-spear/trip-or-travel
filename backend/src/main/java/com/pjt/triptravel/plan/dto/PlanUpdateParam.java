package com.pjt.triptravel.plan.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanUpdateParam {

    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<PlanItemParam> planItems;
}
