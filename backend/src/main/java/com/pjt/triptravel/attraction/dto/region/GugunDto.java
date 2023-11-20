package com.pjt.triptravel.attraction.dto.region;

import com.pjt.triptravel.attraction.entity.region.Gugun;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GugunDto {

	private Long gugunCode;
	private String gugunName;

	public static GugunDto of(Gugun gugun) {
		return GugunDto.builder()
			.gugunCode(gugun.getCode())
			.gugunName(gugun.getName())
			.build();
	}
}
