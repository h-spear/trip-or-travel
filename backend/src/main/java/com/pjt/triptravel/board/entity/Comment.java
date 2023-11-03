package com.pjt.triptravel.board.entity;

import com.pjt.triptravel.common.entity.BaseEntity;
import com.pjt.triptravel.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private Member commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

}
