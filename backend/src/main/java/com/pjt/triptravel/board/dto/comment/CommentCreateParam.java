package com.pjt.triptravel.board.dto.comment;

import lombok.Data;

@Data
public class CommentCreateParam {

	private String comment;
	private Long parentCommentId;
}
