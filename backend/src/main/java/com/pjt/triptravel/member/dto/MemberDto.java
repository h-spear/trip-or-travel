package com.pjt.triptravel.member.dto;

import com.pjt.triptravel.member.entity.Member;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class MemberDto {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private int age;
    private Gender gender;
    private Address address;
    private String profileImageUrl;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .age(member.getAge())
                .gender(member.getGender())
                .address(member.getAddress())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
