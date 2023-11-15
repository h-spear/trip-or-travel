package com.pjt.triptravel.attraction.controller;

import com.pjt.triptravel.attraction.dto.AttractionAroundCondition;
import com.pjt.triptravel.attraction.dto.AttractionSearchCondition;
import com.pjt.triptravel.attraction.service.AttractionService;
import com.pjt.triptravel.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public ApiResponse<?> search(AttractionSearchCondition condition, Pageable pageable) {
        return ApiResponse.ofSuccess(attractionService.search(condition, pageable));
    }

    @GetMapping("/around")
    public ApiResponse<?> around(AttractionAroundCondition condition) {
        return ApiResponse.ofSuccess(attractionService.findWithinRoundRange(condition));
    }

    @GetMapping("/{contentId}")
    public ApiResponse<?> findOne(@PathVariable Long contentId) {
        return ApiResponse.ofSuccess(attractionService.findOne(contentId));
    }
}
