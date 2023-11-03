package com.pjt.triptravel.board.dto.board;

import com.pjt.triptravel.board.entity.Board;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardDto {

    private Long id;
    private String name;
    private String description;

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .build();
    }
}
