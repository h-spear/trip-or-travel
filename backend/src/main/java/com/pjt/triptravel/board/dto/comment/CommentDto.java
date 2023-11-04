package com.pjt.triptravel.board.dto.comment;

import java.util.List;
import java.util.stream.Collectors;

import com.pjt.triptravel.board.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentDto {
	private Long commentId;
	private String comment;
	private Long commenterId;
	private String commenterName;
	private String commenterProfileImageUrl;
	private List<CommentDto> children;

	public static CommentDto of(Comment comment) {
		return CommentDto.builder()
			.commentId(comment.getId())
			.comment(comment.getComment())
			.commenterId(comment.getCommenter().getId())
			.commenterName(comment.getCommenter().getName())
			.commenterProfileImageUrl(comment.getCommenter().getProfileImageUrl())
			.children(comment.getChildren().stream().map(CommentDto::of).collect(Collectors.toList()))
			.build();
	}

	public int getCount() {
		return 1 + children.size();
	}
}
