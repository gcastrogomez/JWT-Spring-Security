package com.gcastro.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gcastro.entities.User;
import com.gcastro.services.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtRequestFilter extends BasicAuthenticationFilter {

	private JwtUserDetailsService jwtUserDetailsService;
	private JwtTokenUtil jwtTokenUtil;
	private List<String> pathsAdmin = Arrays.asList("/hello", "/admin");
	private List<String> pathsUsers = Arrays.asList("/hello");

	public JwtRequestFilter(AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService,
			JwtTokenUtil jwtTokenUtil) {
		super(authenticationManager);
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtUserDetailsService = jwtUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			User userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				// Here, we could control user permissions to certain endpoints
				// if(path().equals("admin") && userDetails.tipo().equals("ADMIN")) {
				// }
				// We tell spring-security that everything is OK
				if (userDetails.getRole().equals("ADMIN")) {
					if (pathsAdmin.contains(request.getRequestURI().toString())) {
						authenticate(request, userDetails);
					}
				} else {
					if (pathsUsers.contains(request.getRequestURI().toString())) {
						authenticate(request, userDetails);
					}

				}
			}
		}
		System.out.println(request.getRequestURL().toString());
		chain.doFilter(request, response);
	}

	private void authenticate(HttpServletRequest request, User userDetails) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, new ArrayList<>());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}

}