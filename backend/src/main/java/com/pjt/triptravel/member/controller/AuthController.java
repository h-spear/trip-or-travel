package com.pjt.triptravel.member.controller;

import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.dto.LoginParam;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Login/Logout API")
public class AuthController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginParam param,
                                HttpSession session) {
        Member member = memberService.login(param.getEmail(), param.getPassword());
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return ApiResponse.ofSuccess();
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 수행합니다.")
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session) {
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        return ApiResponse.ofSuccess();
    }
}
