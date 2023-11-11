package com.pjt.triptravel.common.jwt;

import org.springframework.beans.factory.annotation.Value;

public class TokenConst {

	public static final String ACCESS_TOKEN = "Authorization";
	public static final String REFRESH_TOKEN = "Refresh_Token";
	public static final String SUBJECT_ID = "subjectId";

	public static final int ACCESS_TOKEN_VALIDITY = 30 * 60;
	public static final int REFRESH_TOKEN_VALIDITY = 14 * 24 * 60 * 60;

	public static final String ACCESS_SECRET_KEY = "accesssecretkey";

	public static final String REFRESH_SECRET_KEY = "refreshsecretkey";
}
