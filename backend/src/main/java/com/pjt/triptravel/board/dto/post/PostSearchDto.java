package com.pjt.triptravel.board.dto.post;

import com.pjt.triptravel.board.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostSearchDto {

    private Long postId;
    private String title;
    private String content;
    private int commentCount;
    private String writerName;
    private LocalDateTime registrationDate;
    private LocalDateTime lastModifiedDate;
}
