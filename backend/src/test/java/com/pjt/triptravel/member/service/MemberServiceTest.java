package com.pjt.triptravel.member.service;

import com.pjt.triptravel.member.dto.Address;
import com.pjt.triptravel.member.dto.Gender;
import com.pjt.triptravel.member.dto.MemberRegisterParam;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "pass";
    private static final String NAME = "tester";
    private static final int AGE = 25;
    private static final Address ADDRESS = new Address("서울특별시", "동대문구");
    private static final Gender GENDER = Gender.MALE;
    private static final String PROFILE_IMAGE_URL = "imageURL";

    @Test
    @DisplayName("회원가입에 성공한다.")
    void joinTest_Success() {
        // given
        MemberRegisterParam param = getRegisterParam();
        Member member = getMember();

        // mock
        when(memberRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(passwordEncoder.encode(PASSWORD)).thenReturn("encodedPassword");

        // when
        Long savedId = memberService.join(param);

        // assert
        assertThat(savedId).isEqualTo(MEMBER_ID);
        verify(memberRepository, times(1)).findByEmail(EMAIL);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    private static Member getMember() {
        Member member = Member.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .age(AGE)
                .address(ADDRESS)
                .gender(GENDER)
                .profileImageUrl(PROFILE_IMAGE_URL)
                .build();
        ReflectionTestUtils.setField(member, "id", MEMBER_ID);
        return member;
    }

    private static MemberRegisterParam getRegisterParam() {
        MemberRegisterParam param = new MemberRegisterParam();
        param.setEmail(EMAIL);
        param.setPassword(PASSWORD);
        param.setPasswordConfirm(PASSWORD);
        param.setName(NAME);
        param.setAge(AGE);
        param.setAddress(ADDRESS);
        param.setGender(GENDER);
        param.setProfileImageUrl(PROFILE_IMAGE_URL);
        return param;
    }
}