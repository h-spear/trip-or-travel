package com.pjt.triptravel.plan.controller;

import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.plan.dto.PlanCreateParam;
import com.pjt.triptravel.plan.dto.PlanUpdateParam;
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
    public ApiResponse<?> detail(@Login Long memberId,
                                 @PathVariable Long planId) {
        log.info("여행 계획 상세 조회, planId={}", planId);
        return ApiResponse.ofSuccess(planService.getDetail(memberId, planId));
    }

    @PutMapping("/{planId}")
    public ApiResponse<?> modify(@Login Long memberId,
                                 @PathVariable Long planId,
                                 @RequestBody PlanUpdateParam param) {
        log.info("여행 계획 수정, planId={}", planId);
        planService.modify(memberId, planId, param);
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping("/{planId}")
    public ApiResponse<?> delete(@Login Long memberId,
                                 @PathVariable Long planId) {
        log.info("여행 계획 삭제, planId={}", planId);
        planService.delete(memberId, planId);
        return ApiResponse.ofSuccess();
    }
}
