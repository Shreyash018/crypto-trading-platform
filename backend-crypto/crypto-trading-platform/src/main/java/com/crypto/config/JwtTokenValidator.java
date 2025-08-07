package com.crypto.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crypto.exception.UserException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt != null) {
			jwt = jwt.substring(7); // "Bearer Token" we need to extract 1st 7 character and get only token from
									// front end user will send token with Bearer Keyword.

			try {

				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

				String email = String.valueOf(claims.get("email"));

				String authorities = String.valueOf(claims.get("authorities"));

				System.out.println("authorities -------- " + authorities);

				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				// JWT stores authorities as a comma-separated string, like
				// "ROLE_USER,ROLE_ADMIN".
				// This line converts that string into a List of GrantedAuthority objects, which
				// Spring Security understands.

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				// Creates a Spring Security Authentication object. Email is used as the
				// principal (username).
				// Password is null because JWT already verifies identity.auths contain the
				// roles/authorities.

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				throw new UserException("Invalid Token...");
			}
		}

		filterChain.doFilter(request, response);

		// "I'm done processing this request. Pass it on to the next filter (or
		// controller)."
		// If you don’t call doFilter(...), the request stops there — it will never
		// reach the controller.
		// This would cause your endpoints to hang or return a blank/403/404 response.
	}
}
