package com.pjt.triptravel.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pjt.triptravel.member.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

	Optional<Auth> findByRefreshToken(String refreshToken);
	Optional<Auth> findByEmail(String email);
}
