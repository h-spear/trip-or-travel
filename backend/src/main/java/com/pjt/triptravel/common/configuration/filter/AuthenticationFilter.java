package com.pjt.triptravel.common.configuration.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.pjt.triptravel.common.jwt.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request,
						 ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String requestURI = httpRequest.getRequestURI();
		String accessToken = JwtTokenUtils.resolveAccessToken(httpRequest);
		String refreshToken = JwtTokenUtils.resolveRefreshToken(httpRequest);

		log.info("requestURI={}", requestURI);
		log.info("accessToken={}", accessToken);
		log.info("refreshToken={}", refreshToken);
		// if (accessToken != null && JwtTokenUtils.validateAccessToken(accessToken)) {
		// 	SecurityContextUtils.setAuthentication(accessToken);
		// } else if (isAuthenticationCheckPath(requestURI)) {
		// 	if (refreshToken != null) {
		// 		log.info("액세스 토큰 만료. 리프레시");
		// 		httpResponse.sendRedirect("/refresh?redirectURL=" + requestURI);
		// 	} else {
		// 		log.info("미인증 사용자 요청 {}", requestURI);
		// 		httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
		// 	}
		// 	return;
		// }
		chain.doFilter(httpRequest, httpResponse);
	}
}
