package com.pjt.triptravel.board.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

import com.pjt.triptravel.common.utils.TimeFormatUtil;
import com.querydsl.core.annotations.QueryProjection;

@Data
public class PostSearchResult {

    private Long boardId;
    private Long postId;
    private String title;
    private int commentCount;
    private int views;
    private int likes;
    private Long writerId;
    private String writerNickname;
    private String writerProfileImageUrl;
    private String registrationDate;
    private String lastModifiedDate;

    @QueryProjection
    public PostSearchResult(Long boardId, Long postId, String title, int commentCount, int views, int likes, Long writerId, String writerNickname, String writerProfileImageUrl, LocalDateTime registrationDate, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.postId = postId;
        this.title = title;
        this.commentCount = commentCount;
        this.views = views;
        this.likes = likes;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImageUrl = writerProfileImageUrl;
        this.registrationDate = TimeFormatUtil.convertDateTime(registrationDate);
        this.lastModifiedDate = TimeFormatUtil.convertDateTime(lastModifiedDate);
    }
}
