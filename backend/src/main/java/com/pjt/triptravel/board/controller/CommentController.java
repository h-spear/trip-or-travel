package com.pjt.triptravel.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.pjt.triptravel.board.dto.comment.CommentCreateParam;
import com.pjt.triptravel.board.dto.comment.CommentUpdateParam;
import com.pjt.triptravel.board.service.CommentService;
import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("{postId}/comment")
	public ApiResponse<?> write(@Login Long memberId,
								@PathVariable Long postId,
								@RequestBody CommentCreateParam param) {
		Long commentId = commentService.write(memberId, postId, param);
		return ApiResponse.ofSuccess(commentId);
	}

	@DeleteMapping("{postId}/comment/{commentId}")
	public ApiResponse<?> delete(@Login Long memberId,
								 @PathVariable Long postId,
								 @PathVariable Long commentId) {
		commentService.delete(memberId, commentId);
		return ApiResponse.ofSuccess();
	}

	@PutMapping("{postId}/comment/{commentId}")
	public ApiResponse<?> update(@Login Long memberId,
								 @PathVariable Long postId,
								 @PathVariable Long commentId,
								 @RequestBody CommentUpdateParam param) {
		commentService.update(memberId, commentId, param);
		return ApiResponse.ofSuccess();
	}
}
