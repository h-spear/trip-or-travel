package com.pjt.triptravel.member.controller;

import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.member.dto.MemberRegisterParam;
import com.pjt.triptravel.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ApiResponse<?> join(@RequestBody MemberRegisterParam param) {
        Long savedId = memberService.join(param);
        return ApiResponse.ofSuccess(savedId);
    }

}
