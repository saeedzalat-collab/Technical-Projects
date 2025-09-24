package com.appsdeveloperblog.app.ws;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.info.Contact;
@Configuration
@OpenAPIDefinition(
		info = @Info(
		        title = "Photo App RESTful Web Service Documentation",
		        version = "1.0",
		        description = "This page documents Photo app RESTful Web Service endpoints",
		        contact = @Contact(
		            name = "Eng. Saeed Zalat",
		            url = "https://www.yourwebsite.com",
		            email = "saeedzalat8@gmail.com"
		        )
		    ),
			    servers = {
			            @Server(url = "http://localhost:8088", description = "Local Server"),
			            @Server(url = "https://api.yourdomain.com", description = "Production Server")
			        }
		)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "Bearer",
    bearerFormat = "JWT"
)


public class SwaggerConfig {

}
