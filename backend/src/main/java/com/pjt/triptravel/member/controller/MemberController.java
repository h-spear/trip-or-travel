package com.pjt.triptravel.member.controller;

import com.pjt.triptravel.common.configuration.annotation.Login;
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

    @Operation
    @GetMapping("/{memberId}")
    public ApiResponse<?> detail(@PathVariable Long memberId, @Login Long id) {
        if (memberId.equals(id)) {
            return ApiResponse.ofSuccess(memberService.findOne(id));
        } else {
            return ApiResponse.ofFail("준비중입니다.");
        }
    }

    @Operation
    @GetMapping("/simple")
    public ApiResponse<?> simple(@Login Long id) {
        return ApiResponse.ofSuccess(memberService.findSimple(id));
    }

    // 회원정보 수정, 삭제
}
