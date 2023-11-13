package com.pjt.triptravel.member.dto;

import lombok.Data;

@Data
public class MemberRegisterParam {

    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private String nickname;
    private int age;

    private Gender gender;

    private Address address;

    private String profileImageUrl;

}
