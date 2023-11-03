package com.pjt.triptravel.board.controller;

import com.pjt.triptravel.board.dto.board.BoardCreateParam;
import com.pjt.triptravel.board.dto.board.BoardDto;
import com.pjt.triptravel.board.dto.board.BoardUpdateParam;
import com.pjt.triptravel.board.service.BoardService;
import com.pjt.triptravel.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ApiResponse<?> search() {
        log.info("게시판 목록 조회");
        return ApiResponse.ofSuccess(boardService.findAll());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody BoardCreateParam param) {
        log.info("게시판 생성, name={}", param.getName());
        Long boardId = boardService.create(param);
        return ApiResponse.ofSuccess(boardId);
    }

    @PutMapping("/{boardId}")
    public ApiResponse<?> update(@PathVariable Long boardId, @RequestBody BoardUpdateParam param) {
        log.info("게시판 수정, id={}", boardId);
        boardService.update(boardId, param);
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<?> delete(@PathVariable Long boardId) {
        log.info("게시판 삭제, id={}", boardId);
        boardService.delete(boardId);
        return ApiResponse.ofSuccess();
    }
}
