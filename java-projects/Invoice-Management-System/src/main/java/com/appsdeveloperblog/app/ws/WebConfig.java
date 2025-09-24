package com.appsdeveloperblog.app.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/**"); means access to all rest controller methods 
		registry
		.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*");
	}

}
