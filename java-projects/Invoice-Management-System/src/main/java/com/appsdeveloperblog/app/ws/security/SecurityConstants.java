package com.appsdeveloperblog.app.ws.security;

import org.springframework.core.env.Environment;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 846000000; //10 days
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_SECRET = "THLiJHLkQF9IiHAPtDddI8eT6eYCfuRIPCceeiamaYeTg2Fi9mmZhS1QMKawBrXWjdgYeC2bEB+IXp9qoBJPEA==";
	public static final String H2_CONSOLE = "/h2-console/**";
	
	public static String getTokenSecret()
	{
		Environment environment =  (Environment) SpringApplicationContext.getBean("environment");
		return environment.getProperty("tokenSecret");
	}

}
