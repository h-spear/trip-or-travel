package com.pjt.triptravel.attraction.controller;

import com.pjt.triptravel.attraction.dto.attraction.AttractionAroundCondition;
import com.pjt.triptravel.attraction.dto.attraction.AttractionSearchCondition;
import com.pjt.triptravel.attraction.service.AttractionService;
import com.pjt.triptravel.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
@Api(tags = "Attraction API")
public class AttractionController {

    private final AttractionService attractionService;

    @Operation(summary = "관광지 목록 조회", description = "관광지 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<?> search(AttractionSearchCondition condition, Pageable pageable) {
        return ApiResponse.ofSuccess(attractionService.search(condition, pageable));
    }

    @Operation(summary = "주변 관광지 조회", description = "현재 위도, 경도를 기준으로 특정 km 내의 관광지를 조회합니다.")
    @GetMapping("/around")
    public ApiResponse<?> around(AttractionAroundCondition condition) {
        // 특정 거리(km)이상 조회 못하게 제약 걸어야 함
        return ApiResponse.ofSuccess(attractionService.findWithinRoundRange(condition));
    }

    @Operation(summary = "관광지 상세 조회", description = "관광지 상세 정보를 조회합니다.")
    @GetMapping("/{contentId}")
    public ApiResponse<?> findOne(@PathVariable Long contentId) {
        return ApiResponse.ofSuccess(attractionService.findOne(contentId));
    }
}
