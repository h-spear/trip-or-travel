package com.pjt.triptravel.board.dto.post;

import lombok.Data;

@Data
public class PostCreateParam {

    private Long boardId;
    private String title;
    private String content;

}
