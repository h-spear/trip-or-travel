package com.pjt.triptravel.plan.controller;

import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.plan.dto.PlanCreateParam;
import com.pjt.triptravel.plan.service.PlanService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
@Api(tags = "Plan API")
public class PlanController {
    
    private final PlanService planService;
    
    @PostMapping
    public ApiResponse<?> create(@Login Long memberId,
                                 @RequestBody PlanCreateParam param) {
        log.info("여행 계획 생성, name={}", param.getTitle());
        Long planId = planService.createPlan(memberId, param);
        return ApiResponse.ofSuccess(planId);
    }

    @GetMapping
    public ApiResponse<?> list(@Login Long memberId) {
        log.info("여행 계획 조회, memberId={}", memberId);
        return ApiResponse.ofSuccess(planService.getPlans(memberId));
    }

    @GetMapping("/{planId}")
    public ApiResponse<?> detail(@PathVariable Long planId) {
        log.info("여행 계획 상제 조회, planId={}", planId);
        return ApiResponse.ofSuccess(planService.getDetail(planId));
    }
}
