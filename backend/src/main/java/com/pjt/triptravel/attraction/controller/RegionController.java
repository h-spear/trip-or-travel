package com.pjt.triptravel.attraction.controller;

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
public class RegionController {

	private final RegionService regionService;

	@GetMapping("/sido")
	public ApiResponse<?> findAllSido() {
		return ApiResponse.ofSuccess(regionService.findAllSido());
	}

	@GetMapping("/sido/{sidoCode}")
	public ApiResponse<?> findGugunSidoCode(@PathVariable Long sidoCode) {
		return ApiResponse.ofSuccess(regionService.findGugunBySidoCode(sidoCode));
	}
}
