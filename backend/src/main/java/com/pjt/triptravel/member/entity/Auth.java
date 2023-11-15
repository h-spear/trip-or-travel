package com.pjt.triptravel.member.entity;

import javax.persistence.*;

import lombok.Getter;

@Getter
@Entity
public class Auth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
