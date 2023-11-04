package com.pjt.triptravel.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.triptravel.board.dto.post.PostCreateParam;
import com.pjt.triptravel.board.dto.post.PostSearchCondition;
import com.pjt.triptravel.board.dto.post.PostSearchResult;
import com.pjt.triptravel.board.dto.post.PostUpdateParam;
import com.pjt.triptravel.board.service.PostService;
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
@Api(tags = "Post API")
public class PostController {

	private final PostService postService;

	@Operation(summary = "게시글 조회", description = "postId에 해당하는 게시글을 조회합니다.")
	@GetMapping("/{postId}")
	public ApiResponse<?> detail(@PathVariable Long postId) {
		log.info("게시글 Detail, id={}", postId);
		return ApiResponse.ofSuccess(postService.findOne(postId));
	}

	@Operation(summary = "게시글 검색", description = "게시글을 검색합니다.")
	@GetMapping
	public ApiResponse<?> search(PostSearchCondition condition, Pageable pageable) {
		log.info("검색 조건: {}", condition);
		Page<PostSearchResult> result = postService.search(condition, pageable);
		return ApiResponse.ofSuccess(result);
	}

	@Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
	@PostMapping
	public ApiResponse<?> posting(@Login Long memberId,
								  @RequestBody PostCreateParam param) {
		log.info("게시글 생성, writerId={}", memberId);
		Long postId = postService.posting(memberId, param);
		return ApiResponse.ofSuccess(postId);
	}

	@Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
	@PutMapping("/{postId}")
	public ApiResponse<?> update(@Login Long memberId,
								 @PathVariable Long postId,
								 @RequestBody PostUpdateParam param) {
		log.info("게시글 수정, postId={}, writerId={}", postId, memberId);
		postService.update(postId, memberId, param);
		return ApiResponse.ofSuccess();
	}

	@Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
	@DeleteMapping("/{postId}")
	public ApiResponse<?> delete(@Login Long memberId,
								 @PathVariable Long postId) {
		log.info("게시글 삭제, postId={}, writerId={}", postId, memberId);
		postService.delete(postId, memberId);
		return ApiResponse.ofSuccess();
	}

}
