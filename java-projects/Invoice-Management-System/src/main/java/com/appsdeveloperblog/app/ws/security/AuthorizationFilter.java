package com.appsdeveloperblog.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.appsdeveloperblog.app.ws.io.Repository.UserRepository;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	private final 	UserRepository userRepository;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(SecurityConstants.HEADER_STRING); //read request header with name authorization
		
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response); //pass execution to the next filter chain   
			return;
		}// checking if there is no JWT access token or not starts with bearer
		
		//if web JWT is valid this line will return user-name and password from request
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request); 
		
		//after that we must add this authentication object to security context holder to make sure that user is authenticated 
		//to complete the life-cycle
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	    String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

	    if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
	        return null;
	    }

	    // Extract the JWT token
	    String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

	    try {
	        // Convert Base64 key back to a SecretKey
	        byte[] secretKeyBytes = Base64.getDecoder().decode(SecurityConstants.getTokenSecret());
	        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

	        // Parse the token
	        JwtParser parser = Jwts.parser()
	                .verifyWith(key)
	                .build();

	        Claims claims = parser.parseSignedClaims(token).getPayload();
	        String subject = claims.getSubject(); // Extract the username (subject)

	        if (subject == null) {
	            return null;
	        }
	        
	        UserEntity userEntity = userRepository.findByEmail(subject);
	        UserPrincipal userPrincipal = new UserPrincipal(userEntity);
	        
	        return new UsernamePasswordAuthenticationToken(subject, null, userPrincipal.getAuthorities());
	    } catch (Exception e) {
	        return null; // Return null if the JWT is invalid
	    }
	}

}
