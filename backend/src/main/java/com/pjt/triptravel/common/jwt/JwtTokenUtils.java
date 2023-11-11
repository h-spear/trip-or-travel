package com.pjt.triptravel.common.jwt;

import static com.pjt.triptravel.common.jwt.TokenConst.*;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenUtils {

	public static Long extractMemberId(String accessToken) {
		return Long.parseLong(
			Jwts.parser().setSigningKey(TokenConst.ACCESS_SECRET_KEY).parseClaimsJws(accessToken).getBody().get(TokenConst.SUBJECT_ID, String.class));
	}

	public static boolean validateAccessToken(String accessToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(TokenConst.ACCESS_SECRET_KEY).parseClaimsJws(accessToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateRefreshToken(String refreshToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(TokenConst.REFRESH_SECRET_KEY).parseClaimsJws(refreshToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public static String resolveAccessToken(HttpServletRequest request) {
		return Arrays.stream(getCookies(request))
			.filter(cookie -> cookie.getName().equals(TokenConst.ACCESS_TOKEN))
			.map(cookie -> cookie.getValue())
			.findFirst()
			.orElse(null);
	}

	public static String resolveRefreshToken(HttpServletRequest request) {
		return Arrays.stream(getCookies(request))
			.filter(cookie -> cookie.getName().equals(TokenConst.REFRESH_TOKEN))
			.map(cookie -> cookie.getValue())
			.findFirst()
			.orElse(null);
	}

	private static Cookie[] getCookies(HttpServletRequest request) {
		return request.getCookies() == null ? new Cookie[0] : request.getCookies();
	}

	public static void setCookieAccessToken(HttpServletResponse response, String accessToken) {
		Cookie cookie = new Cookie(TokenConst.ACCESS_TOKEN, accessToken);
		cookie.setPath("/");
		cookie.setMaxAge(ACCESS_TOKEN_VALIDITY);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		response.addCookie(cookie);
	}

	public static void setCookieRefreshToken(HttpServletResponse response, String refreshToken) {
		Cookie cookie = new Cookie(TokenConst.REFRESH_TOKEN, refreshToken);
		cookie.setPath("/");
		cookie.setMaxAge(REFRESH_TOKEN_VALIDITY);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		response.addCookie(cookie);
	}
}
