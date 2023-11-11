package com.pjt.triptravel.common.configuration.argumentResolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.common.jwt.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		log.info("supportsParameter 실행");
		boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginAnnotation && hasLongType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		log.info("resolveArgument 실행");

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		String accessToken = JwtTokenUtils.resolveAccessToken(request);

		if (accessToken == null) {
			throw new UserNotFoundException();
		}
		return JwtTokenUtils.extractMemberId(accessToken);
	}
}
