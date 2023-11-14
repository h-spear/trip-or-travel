package com.pjt.triptravel.attraction.dto.region;

import com.pjt.triptravel.attraction.entity.Gugun;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GugunDto {

	private Long gugunCode;
	private String gugunName;
	private String sidoName;

	public static GugunDto of(Gugun gugun) {
		return GugunDto.builder()
			.gugunCode(gugun.getCode())
			.gugunName(gugun.getName())
			.sidoName(gugun.getSido().getName())
			.build();
	}
}
