package com.pjt.triptravel.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.pjt.triptravel.board.dto.comment.CommentDto;
import com.pjt.triptravel.board.dto.post.PostCreateParam;
import com.pjt.triptravel.board.dto.post.PostDetailDto;
import com.pjt.triptravel.board.dto.post.PostSearchCondition;
import com.pjt.triptravel.board.dto.post.PostSearchResult;
import com.pjt.triptravel.board.dto.post.PostUpdateParam;
import com.pjt.triptravel.board.service.PostService;
import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.entity.Member;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	@GetMapping("/{postId}")
	public ApiResponse<?> detail(@PathVariable Long postId) {
		log.info("게시글 Detail, id={}", postId);
		return ApiResponse.ofSuccess(postService.findOne(postId));
	}

	@GetMapping
	public ApiResponse<?> search(PostSearchCondition condition, Pageable pageable) {
		log.info("검색 조건: {}", condition);
		Page<PostSearchResult> result = postService.search(condition, pageable);
		return ApiResponse.ofSuccess(result);
	}

	@PostMapping
	public ApiResponse<?> posting(@Login Long memberId,
								  @RequestBody PostCreateParam param) {
		log.info("게시글 생성, writerId={}", memberId);
		Long postId = postService.posting(memberId, param);
		return ApiResponse.ofSuccess(postId);
	}

	@PutMapping("/{postId}")
	public ApiResponse<?> update(@Login Long memberId,
								 @PathVariable Long postId,
								 @RequestBody PostUpdateParam param) {
		log.info("게시글 수정, postId={}, writerId={}", postId, memberId);
		postService.update(postId, memberId, param);
		return ApiResponse.ofSuccess();
	}

	@DeleteMapping("/{postId}")
	public ApiResponse<?> delete(@Login Long memberId,
								 @PathVariable Long postId) {
		log.info("게시글 삭제, postId={}, writerId={}", postId, memberId);
		postService.delete(postId, memberId);
		return ApiResponse.ofSuccess();
	}

}
