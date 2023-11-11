package com.pjt.triptravel.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.common.jwt.JwtTokenProvider;
import com.pjt.triptravel.member.dto.LoginParam;
import com.pjt.triptravel.member.dto.Token;
import com.pjt.triptravel.member.entity.Auth;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.AuthRepository;
import com.pjt.triptravel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AuthRepository authRepository;
	private final MemberRepository memberRepository;

	public Token login(LoginParam param) {
		log.info("로그인 서비스 실행");

		Member member = memberRepository.findByEmail(param.getEmail())
			.orElseThrow(UserNotFoundException::new);

		if (!member.getPassword().equals(param.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		Token token = JwtTokenProvider.createToken(member.getId(), member.getEmail());
		String refreshToken = token.getRefreshToken();

		Optional<Auth> optional = authRepository.findByEmail(member.getEmail());
		if (optional.isPresent()) {
			Auth auth = optional.get();
			auth.setRefreshToken(refreshToken);
		} else {
			Auth auth = new Auth(member.getId(), member.getEmail(), refreshToken);
			authRepository.save(auth);
		}
		return token;
	}
}
