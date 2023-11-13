package com.pjt.triptravel.member.dto;

import com.pjt.triptravel.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSimpleDto {

    private Long id;
    private String nickname;
    private String profileImageUrl;

    public static MemberSimpleDto of(Member member) {
        return MemberSimpleDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
