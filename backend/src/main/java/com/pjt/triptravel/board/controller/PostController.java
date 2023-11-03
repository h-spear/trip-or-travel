package com.pjt.triptravel.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.pjt.triptravel.board.dto.post.PostCreateParam;
import com.pjt.triptravel.board.dto.post.PostUpdateParam;
import com.pjt.triptravel.board.service.PostService;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.entity.Member;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ApiResponse<?> posting(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
								  @RequestBody PostCreateParam param) {
		if (member == null) {
			return ApiResponse.ofError("로그인이 필요합니다.");
		}
		log.info("게시글 생성, writerId={}", member.getId());
		Long postId = postService.posting(member.getId(), param);
		return ApiResponse.ofSuccess(postId);
	}

	@PutMapping("/{postId}")
	public ApiResponse<?> update(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
								 @PathVariable Long postId,
								 @RequestBody PostUpdateParam param) {
		if (member == null) {
			return ApiResponse.ofError("로그인이 필요합니다.");
		}
		log.info("게시글 수정, postId={}, writerId={}", postId, member.getId());
		postService.update(postId, member.getId(), param);
		return ApiResponse.ofSuccess();
	}

	@DeleteMapping("/{postId}")
	public ApiResponse<?> delete(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
								 @PathVariable Long postId) {
		if (member == null) {
			return ApiResponse.ofError("로그인이 필요합니다.");
		}
		log.info("게시글 삭제, postId={}, writerId={}", postId, member.getId());
		postService.delete(postId, member.getId());
		return ApiResponse.ofSuccess();
	}

}
