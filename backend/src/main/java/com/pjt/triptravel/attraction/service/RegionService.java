package com.pjt.triptravel.attraction.service;

import com.pjt.triptravel.attraction.dto.region.SidoDto;
import com.pjt.triptravel.attraction.repository.region.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final SidoRepository sidoRepository;

	public List<SidoDto> findAllSido() {
		return sidoRepository.findAllWithGugun()
			.stream().map(SidoDto::of)
			.collect(Collectors.toList());
	}
}
