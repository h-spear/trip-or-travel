package com.pjt.triptravel.board.dto.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.pjt.triptravel.board.dto.comment.CommentDto;
import com.pjt.triptravel.board.entity.Comment;
import com.pjt.triptravel.board.entity.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PostDetailDto {

	private Long postId;
	private String title;
	private String content;
	private int commentCount;
	private int views;
	private int likes;
	private Long writerId;
	private String writerName;
	private LocalDateTime registrationDate;
	private LocalDateTime lastModifiedDate;
	private List<CommentDto> comments;

	public static PostDetailDto of(Post post, List<Comment> comments) {
		List<CommentDto> commentDtos = comments.stream()
			.filter(comment -> comment.getParent() == null)
			.map(CommentDto::of)
			.collect(Collectors.toList());
		int commentCount = commentDtos.stream().mapToInt(CommentDto::getCount).sum();

		return PostDetailDto.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.views(post.getViews())
			.likes(post.getLikes())
			.writerId(post.getWriter().getId())
			.writerName(post.getWriter().getName())
			.registrationDate(post.getRegistrationDate())
			.lastModifiedDate(post.getLastModifiedDate())
			.comments(commentDtos)
			.commentCount(commentCount)
			.build();
	}
}

