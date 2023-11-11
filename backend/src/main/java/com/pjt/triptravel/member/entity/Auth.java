package com.pjt.triptravel.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Auth {

	@Id
	@GeneratedValue
	@Column(name = "AUTH_ID", nullable = false)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String refreshToken;

	protected Auth() {
	}

	public Auth(Long userId, String email, String refreshToken) {
		this.userId = userId;
		this.email = email;
		this.refreshToken = refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
