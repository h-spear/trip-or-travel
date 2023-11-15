package com.pjt.triptravel.common.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.triptravel.common.jwt.JwtTokenProvider;
import com.pjt.triptravel.common.jwt.JwtTokenUtils;
import com.pjt.triptravel.common.response.ApiResponse;
import com.pjt.triptravel.common.security.SecurityContextUtils;
import com.pjt.triptravel.member.entity.Auth;
import com.pjt.triptravel.member.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.RefreshFailedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final String[] whiteList = {"/", "/home", "/member/join", "/member/email", "/member/nickname",
												"/login", "/logout", "/refresh", "/region/**", "/attraction", "/attraction/**"};

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
			log.info("Access Token invalidate. Refresh!!!");
			reissueAccessToken(response, refreshToken);
			return;
		}
		chain.doFilter(request, response);
	}

	private void reissueAccessToken(HttpServletResponse response, String refreshToken) throws IOException {
		try {
			if (refreshToken == null || !JwtTokenUtils.validateRefreshToken(refreshToken)) {
				throw new RefreshFailedException();
			}
			Auth auth = authRepository.findByRefreshToken(refreshToken).orElseThrow(RefreshFailedException::new);
			String accessToken = JwtTokenProvider.createAccessToken(auth.getUserId(), auth.getEmail(), new Date());
			JwtTokenUtils.setCookieAccessToken(response, accessToken);
			log.info("Reissue Access Token{}", accessToken);
			setApiResponseFail(response, "Access Token 재발급");
		} catch (RefreshFailedException e) {
			log.info("미인증 사용자 요청 - 로그인이 필요합니다.");
			setApiResponseFail(response, "로그인이 필요합니다.");
		}
	}

	private boolean isAuthenticationCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}

	private void setApiResponseFail(HttpServletResponse response, String failMessage) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getWriter(), ApiResponse.ofFail(failMessage));
	}
}
