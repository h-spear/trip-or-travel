package com.pjt.triptravel.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pjt.triptravel.common.response.ApiResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.pjt.triptravel.common.configuration.filter.AuthenticationFilter;
import com.pjt.triptravel.common.jwt.TokenConst;
import com.pjt.triptravel.common.security.SecurityContextUtils;
import com.pjt.triptravel.common.security.UserRole;
import com.pjt.triptravel.member.repository.AuthRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String[] SWAGGER = {
			"/v3/api-docs", "/v2/api-docs",
			"/swagger-resources/**", "/configuration/security", "/webjars/**",
			"/swagger-ui.html", "/swagger/**", "/swagger-ui/**"};

	private final SecurityContextUtils securityContextUtils;
	private final AuthRepository authRepository;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.httpBasic().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
			.antMatchers("/**").permitAll();

		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(new LogoutSuccessHandler() {
				@Override
				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setHeader("Access-Control-Allow-Origin", "http://localhost:9000");
					response.setHeader("Access-Control-Allow-Methods", "POST");
					response.setHeader("Access-Control-Max-Age", "3600");
					response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
					response.setHeader("Access-Control-Allow-Credentials", "true");

					ObjectMapper objectMapper = new ObjectMapper();
					String result = objectMapper.writeValueAsString(ApiResponse.ofSuccess());
					response.getWriter().write(result);
				}
			})
			.deleteCookies(TokenConst.ACCESS_TOKEN, TokenConst.REFRESH_TOKEN);

		http.addFilterBefore(new AuthenticationFilter(securityContextUtils, authRepository),
			UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.antMatchers("/error")
			.antMatchers(SWAGGER);
	}
}
