package com.pjt.triptravel.member.controller;

import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.exception.DuplicateException;
import org.springframework.web.bind.annotation.*;

import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.member.dto.MemberRegisterParam;
import com.pjt.triptravel.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(tags = "Member API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "사용자 계정을 생성합니다.")
    @PostMapping("/join")
    public ApiResponse<?> join(@RequestBody MemberRegisterParam param) {
        Long savedId = memberService.join(param);
        return ApiResponse.ofSuccess(savedId);
    }

    @Operation(summary = "회원정보 조회", description = "memberId에 해당하는 회원의 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ApiResponse<?> detail(@PathVariable Long memberId, @Login Long id) {
        if (memberId.equals(id)) {
            return ApiResponse.ofSuccess(memberService.findOne(id));
        } else {
            return ApiResponse.ofFail("준비중입니다.");
        }
    }

    @Operation(summary = "회원정보 약식 조회", description = "현재 로그인한 회원의 정보를 약식 조회합니다.")
    @GetMapping("/simple")
    public ApiResponse<?> simple(@Login Long id) {
        return ApiResponse.ofSuccess(memberService.findSimple(id));
    }

    @Operation(summary = "닉네임 중복 체크", description = "닉네임 중복 여부를 체크합니다.")
    @GetMapping("/nickname")
    public ApiResponse<?> nickname(String nickname) {
        try {
            memberService.validateDuplicateNickname(nickname);
            return ApiResponse.ofSuccess(false);
        } catch (DuplicateException e) {
            return ApiResponse.ofSuccess(true);
        }
    }

    @Operation(summary = "이메일 중복 체크", description = "이메일 중복 여부를 체크합니다.")
    @GetMapping("/email")
    public ApiResponse<?> email(String email) {
        try {
            memberService.validateDuplicateEmail(email);
            return ApiResponse.ofSuccess(false);
        } catch (DuplicateException e) {
            return ApiResponse.ofSuccess(true);
        }
    }

    // 회원정보 수정, 삭제
}
