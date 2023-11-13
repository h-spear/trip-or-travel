package com.pjt.triptravel.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.pjt.triptravel.board.entity.Comment;
import com.pjt.triptravel.board.entity.Post;
import com.pjt.triptravel.common.entity.BaseEntity;
import com.pjt.triptravel.common.security.UserRole;
import com.pjt.triptravel.member.dto.Address;
import com.pjt.triptravel.member.dto.Gender;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    private String profileImageUrl;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments = new ArrayList<>();

    private UserRole role = UserRole.USER;

    protected Member() {

    }

    @Builder
    public Member(String email, String password, String name, String nickname, int age, Gender gender, Address address, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }
}
