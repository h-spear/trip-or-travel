package com.pjt.triptravel.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtil {

	public static String convertDateTime(LocalDateTime dateTime) {
		if (dateTime == null)
			return null;
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
