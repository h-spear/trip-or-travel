package com.pjt.triptravel.board.service;

import com.pjt.triptravel.board.dto.board.BoardCreateParam;
import com.pjt.triptravel.board.dto.board.BoardDto;
import com.pjt.triptravel.board.dto.board.BoardUpdateParam;
import com.pjt.triptravel.board.entity.Board;
import com.pjt.triptravel.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> findAll() {
        return boardRepository.findAll()
                .stream().map(BoardDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(BoardCreateParam param) {
        Board board = boardRepository.save(Board.builder()
                .name(param.getName())
                .description(param.getDescription())
                .build());
        return board.getId();
    }

    @Transactional
    public void update(Long boardId, BoardUpdateParam param) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판 번호입니다."));
        board.update(param.getName(), param.getDescription());
    }

    @Transactional
    public void delete(Long boardId) {
        try {
            boardRepository.deleteById(boardId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 게시판 번호입니다.");
        }
    }
}
