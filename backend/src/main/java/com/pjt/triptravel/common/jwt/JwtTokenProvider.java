package com.pjt.triptravel.common.jwt;

import static com.pjt.triptravel.common.jwt.TokenConst.*;

import java.util.Date;

import com.pjt.triptravel.member.dto.auth.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenProvider {

	public static Token createToken(Long id, String email) {
		log.info("email={}", email);

		Date now = new Date();

		String accessToken = createAccessToken(id, email, now);
		String refreshToken = createRefreshToken(id, email, now);

		return Token.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.key(email).build();
	}

	public static String createAccessToken(Long id, String email, Date iat) {
		return Jwts.builder()
			.setSubject(email)
			.claim(TokenConst.SUBJECT_ID, id.toString())
			.setIssuedAt(iat)
			.setExpiration(new Date(iat.getTime() + ACCESS_TOKEN_VALIDITY * 1000L))
			.signWith(SignatureAlgorithm.HS256, TokenConst.ACCESS_SECRET_KEY)
			.compact();
	}

	private static String createRefreshToken(Long id, String email, Date iat) {
		return Jwts.builder()
			.setSubject(email)
			.claim(TokenConst.SUBJECT_ID, id.toString())
			.setIssuedAt(iat)
			.setExpiration(new Date(iat.getTime() + REFRESH_TOKEN_VALIDITY * 1000L))
			.signWith(SignatureAlgorithm.HS256, TokenConst.REFRESH_SECRET_KEY)
			.compact();
	}
}
