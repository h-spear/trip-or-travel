package com.pjt.triptravel.common.advice;

import com.pjt.triptravel.common.exception.DuplicateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.common.response.ApiResponse;

@RestControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ApiResponse<?> userNotFound() {
		return ApiResponse.ofFail("로그인되지 않았거나, 잘못된 회원 정보입니다.");
	}

	@ExceptionHandler(DuplicateException.class)
	public ApiResponse<?> userDuplicate(DuplicateException e) {
		return ApiResponse.ofFail(e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<?> illegalArgument(DuplicateException e) {
		return ApiResponse.ofFail(e.getMessage());
	}
}
