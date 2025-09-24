package com.appsdeveloperblog.app.ws.shared;

import org.springframework.stereotype.Component;

import com.appsdeveloperblog.app.ws.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.SecureRandom;

import java.util.Random;

@Component
public class Utils {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public String generateUserId(int length)
	{
		return generateRandomString(length);
	}
	
	public String generateAddressId(int length)
	{
		return generateRandomString(length);
	}
	
	private String generateRandomString(int length)
	{
		StringBuilder returnValue = new StringBuilder(length);
		
		for(int i=0; i<length; i++)
		{
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(returnValue);
	}
	

}
