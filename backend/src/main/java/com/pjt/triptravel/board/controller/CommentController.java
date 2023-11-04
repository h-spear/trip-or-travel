package com.pjt.triptravel.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.triptravel.board.dto.comment.CommentCreateParam;
import com.pjt.triptravel.board.dto.comment.CommentUpdateParam;
import com.pjt.triptravel.board.service.CommentService;
import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.response.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api(tags = "Comment API")
public class CommentController {

	private final CommentService commentService;

	@Operation(summary = "댓글 작성", description = "postId에 해당하는 게시글에 댓글을 작성합니다.")
	@PostMapping("{postId}/comment")
	public ApiResponse<?> write(@Login Long memberId,
								@PathVariable Long postId,
								@RequestBody CommentCreateParam param) {
		Long commentId = commentService.write(memberId, postId, param);
		return ApiResponse.ofSuccess(commentId);
	}

	@Operation(summary = "댓글 삭제", description = "commentId에 해당하는 댓글을 삭제합니다.")
	@DeleteMapping("{postId}/comment/{commentId}")
	public ApiResponse<?> delete(@Login Long memberId,
								 @PathVariable Long postId,
								 @PathVariable Long commentId) {
		commentService.delete(memberId, commentId);
		return ApiResponse.ofSuccess();
	}

	@Operation(summary = "댓글 수정", description = "commentId에 해당하는 댓글을 수정합니다.")
	@PutMapping("{postId}/comment/{commentId}")
	public ApiResponse<?> update(@Login Long memberId,
								 @PathVariable Long postId,
								 @PathVariable Long commentId,
								 @RequestBody CommentUpdateParam param) {
		commentService.update(memberId, commentId, param);
		return ApiResponse.ofSuccess();
	}
}
