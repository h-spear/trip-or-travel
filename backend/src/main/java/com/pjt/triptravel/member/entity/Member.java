package com.pjt.triptravel.member.entity;

import com.pjt.triptravel.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

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
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    private String profileImageUrl;

    protected Member() {

    }

    @Builder
    public Member(String email, String password, String name, int age, Gender gender, Address address, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.profileImageUrl = profileImageUrl;
    }
}
