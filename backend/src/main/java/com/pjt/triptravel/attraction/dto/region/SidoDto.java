package com.pjt.triptravel.attraction.dto.region;

import com.pjt.triptravel.attraction.entity.region.Sido;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SidoDto {

	private Long sidoCode;
	private String sidoName;
	private List<GugunDto> guguns;

	public static SidoDto of(Sido sido) {
		List<GugunDto> guguns = sido.getGuguns().stream()
				.map(GugunDto::of)
				.collect(Collectors.toList());

		return SidoDto.builder()
			.sidoCode(sido.getCode())
			.sidoName(sido.getName())
			.guguns(guguns)
			.build();
	}
}
