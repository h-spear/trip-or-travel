package com.pjt.triptravel.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.triptravel.board.dto.board.BoardCreateParam;
import com.pjt.triptravel.board.dto.board.BoardUpdateParam;
import com.pjt.triptravel.board.service.BoardService;
import com.pjt.triptravel.common.response.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Api(tags = "Board API")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판 목록 조회", description = "게시판 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<?> search() {
        log.info("게시판 목록 조회");
        return ApiResponse.ofSuccess(boardService.findAll());
    }

    @Operation(summary = "게시판 조회", description = "게시판 제목, 설명을 조회합니다.")
    @GetMapping("/{boardId}")
    public ApiResponse<?> searchOne(@PathVariable Long boardId) {
        log.info("게시판 조회");
        return ApiResponse.ofSuccess(boardService.findOne(boardId));
    }

    @Operation(summary = "게시판 생성", description = "게시판을 생성합니다.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody BoardCreateParam param) {
        log.info("게시판 생성, name={}", param.getName());
        Long boardId = boardService.create(param);
        return ApiResponse.ofSuccess(boardId);
    }

    @Operation(summary = "게시판 수정", description = "boardId에 해당하는 게시판의 이름, 설명을 수정합니다.")
    @PutMapping("/{boardId}")
    public ApiResponse<?> update(@PathVariable Long boardId, @RequestBody BoardUpdateParam param) {
        log.info("게시판 수정, id={}", boardId);
        boardService.update(boardId, param);
        return ApiResponse.ofSuccess();
    }

    @Operation(summary = "게시판 삭제", description = "boardId에 해당하는 게시판을 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ApiResponse<?> delete(@PathVariable Long boardId) {
        log.info("게시판 삭제, id={}", boardId);
        boardService.delete(boardId);
        return ApiResponse.ofSuccess();
    }
}
