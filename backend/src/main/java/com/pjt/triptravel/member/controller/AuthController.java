package com.pjt.triptravel.member.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pjt.triptravel.common.jwt.JwtTokenUtils;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.security.SecurityContextUtils;
import com.pjt.triptravel.member.dto.auth.LoginParam;
import com.pjt.triptravel.member.dto.auth.Token;
import com.pjt.triptravel.member.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Login/Logout API")
public class AuthController {

    private final AuthService authService;
    private final SecurityContextUtils securityContextUtils;

    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginParam param,
                                HttpServletResponse response) {
        Token token = authService.login(param);
        JwtTokenUtils.setCookieAccessToken(response, token.getAccessToken());
        JwtTokenUtils.setCookieRefreshToken(response, token.getRefreshToken());
        securityContextUtils.setAuthentication(token.getAccessToken());
        return ApiResponse.ofSuccess(token);
    }
}
