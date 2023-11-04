package com.pjt.triptravel.board.dto.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

@Data
public class PostSearchResult {

    private Long postId;
    private String title;
    private String content;
    private int commentCount;
    private int views;
    private int likes;
    private String writerName;
    private LocalDateTime registrationDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public PostSearchResult(Long postId, String title, String content, int commentCount, int views, int likes,
        String writerName, LocalDateTime registrationDate, LocalDateTime lastModifiedDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.views = views;
        this.likes = likes;
        this.writerName = writerName;
        this.registrationDate = registrationDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
