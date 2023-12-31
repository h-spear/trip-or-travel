package com.pjt.triptravel.common.configuration.argumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.pjt.triptravel.common.configuration.annotation.Login;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.common.session.SessionConst;
import com.pjt.triptravel.member.entity.Member;

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
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
		if (loginMember == null) {
			throw new UserNotFoundException();
		}
		log.info("login Member={}, {}", loginMember.getId(), loginMember);
		return loginMember.getId();
	}
}
