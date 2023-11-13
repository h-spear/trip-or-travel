package com.pjt.triptravel.board.dto.post;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostSearchCondition {

	private Long boardId;
	
	private String title;
	private String writer;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	private PostSearchOrder order;
}
