package com.pjt.triptravel.board.entity;

import com.pjt.triptravel.common.entity.BaseEntity;
import com.pjt.triptravel.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    private int likes;
    private int views;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Builder
    public Post(String title, String content, Board board, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        writer.getPosts().add(this);
        setBoard(board);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getPosts().add(this);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
