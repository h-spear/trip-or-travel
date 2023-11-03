package com.pjt.triptravel.member.controller;

import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.dto.LoginParam;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.service.MemberService;
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
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginParam param,
                                HttpSession session) {
        Member member = memberService.login(param.getEmail(), param.getPassword());
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session) {
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        return ApiResponse.ofSuccess();
    }
}
