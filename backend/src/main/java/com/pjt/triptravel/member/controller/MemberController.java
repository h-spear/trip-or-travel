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

    // 회원정보 수정, 삭제, 마이페이지...
}
