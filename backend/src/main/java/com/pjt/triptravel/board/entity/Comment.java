package com.pjt.triptravel.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.pjt.triptravel.common.entity.BaseEntity;
import com.pjt.triptravel.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private Member commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    protected Comment() {

    }

    @Builder
    public Comment(String comment, Member commenter, Post post, Comment parent) {
        this.comment = comment;
        this.commenter = commenter;
        this.post = post;
        setParent(parent);
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    private void setParent(Comment parent) {
        if (parent != null) {
            this.parent = parent;
            parent.getChildren().add(this);
        }
    }
}
