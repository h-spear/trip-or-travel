package com.pjt.triptravel.common.configuration.filter;

import java.io.IOException;
import java.sql.Ref;
import java.util.Date;

import javax.security.auth.RefreshFailedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.common.jwt.JwtTokenProvider;
import com.pjt.triptravel.common.jwt.JwtTokenUtils;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.security.SecurityContextUtils;
import com.pjt.triptravel.member.entity.Auth;
import com.pjt.triptravel.member.repository.AuthRepository;
import com.pjt.triptravel.member.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final String[] whiteList = {"/", "/home", "/member/join", "/login", "/logout", "/refresh"};

	private final SecurityContextUtils securityContextUtils;
	private final AuthRepository authRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String accessToken = JwtTokenUtils.resolveAccessToken(request);
		String refreshToken = JwtTokenUtils.resolveRefreshToken(request);

		log.info("requestURI={}", requestURI);
		log.info("accessToken={}", accessToken);
		log.info("refreshToken={}", refreshToken);
		if (accessToken != null && JwtTokenUtils.validateAccessToken(accessToken)) {
			securityContextUtils.setAuthentication(accessToken);
		} else if (isAuthenticationCheckPath(requestURI)) {
			log.info("액세스 토큰 만료. 리프레시");
			reissueAccessToken(response, requestURI, refreshToken);
			return;
		}
		chain.doFilter(request, response);
	}

	private void reissueAccessToken(HttpServletResponse response, String requestURI, String refreshToken) throws IOException {
		String accessToken;
		try {
			if (refreshToken == null) {
				throw new RefreshFailedException();
			}
			Auth auth = authRepository.findByRefreshToken(refreshToken).orElseThrow(RefreshFailedException::new);
			if (!JwtTokenUtils.validateRefreshToken(refreshToken)) {
				throw new RefreshFailedException();
			}
			accessToken = JwtTokenProvider.createAccessToken(auth.getUserId(), auth.getEmail(), new Date());
			JwtTokenUtils.setCookieAccessToken(response, accessToken);
			log.info("액세스 토큰 재발급 {}", accessToken);
			ObjectMapper objectMapper = new ObjectMapper();
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			objectMapper.writeValue(response.getWriter(), ApiResponse.ofFail("access token 재발급"));
		} catch (RefreshFailedException e) {
			log.info("미인증 사용자 요청 {}", requestURI);
			ObjectMapper objectMapper = new ObjectMapper();
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			objectMapper.writeValue(response.getWriter(), ApiResponse.ofFail("로그인이 필요합니다. requestURI=" + requestURI));
		}
	}

	private boolean isAuthenticationCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}

}
