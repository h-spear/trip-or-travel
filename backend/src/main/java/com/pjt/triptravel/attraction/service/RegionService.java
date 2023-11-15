package com.pjt.triptravel.attraction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pjt.triptravel.attraction.dto.region.GugunDto;
import com.pjt.triptravel.attraction.dto.region.SidoDto;
import com.pjt.triptravel.attraction.repository.region.GugunRepository;
import com.pjt.triptravel.attraction.repository.region.SidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final SidoRepository sidoRepository;
	private final GugunRepository gugunRepository;

	public List<SidoDto> findAllSido() {
		return sidoRepository.findAll()
			.stream().map(SidoDto::of)
			.collect(Collectors.toList());
	}

	public List<GugunDto> findGugunBySidoCode(Long sidoCode) {
		return gugunRepository.findBySidoCode(sidoCode)
			.stream().map(GugunDto::of)
			.collect(Collectors.toList());
	}
}
