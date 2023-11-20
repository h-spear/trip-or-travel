package com.pjt.triptravel.attraction.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.triptravel.attraction.service.RegionService;
import com.pjt.triptravel.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
@Api(tags = "Region API")
public class RegionController {

	private final RegionService regionService;

	@Operation(summary = "시·도·구·군 정보 조회", description = "시·도·구·군 정보를 조회합니다.")
	@GetMapping("/sido")
	public ApiResponse<?> findAllSido() {
		return ApiResponse.ofSuccess(regionService.findAllSido());
	}
}
