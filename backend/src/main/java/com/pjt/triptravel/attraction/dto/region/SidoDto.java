package com.pjt.triptravel.attraction.dto.region;

import com.pjt.triptravel.attraction.entity.region.Sido;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SidoDto {

	private Long sidoCode;
	private String sidoName;

	public static SidoDto of(Sido sido) {
		return SidoDto.builder()
			.sidoCode(sido.getCode())
			.sidoName(sido.getName())
			.build();
	}
}
