package com.appsdeveloperblog.app.ws.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	  
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
               super(authenticationManager);
    }
    
    @Override
    //invoke  loadUserByUsername
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // ✅ Use `Keys.hmacShaKeyFor()` to generate the secret key
    	SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SecurityConstants.getTokenSecret()));
        Instant now = Instant.now();
        String userName = ((UserPrincipal) auth.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .subject(userName)
                .expiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
                .issuedAt(Date.from(now))
                .signWith(secretKey, Jwts.SIG.HS512) // ✅ Updated Signature Algorithm
                .compact();
        
        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);
        
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + ""+token); // 1st response header
        res.addHeader("UserId", userDto.getUserId()); // 2nd response header
    }
 
}

