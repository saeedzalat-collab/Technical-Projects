package com.appsdeveloperblog.app.ws.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.ui.model.request.LoginRequestModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;

@RestController
public class AuthenticationController {
	
	@Operation(summary = "User Login", description = "Performs user login and returns JWT token in the authorization header")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response Headers",
                headers = {
                    @Header(name = "authorization", description = "Bearer <JWT value here>"),
                    @Header(name = "userId", description = "<Public User Id value here>")
                }
            )
        })
	@PostMapping("/users/login")
	public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
		throw new IllegalStateException("This method should not be called. This method is implemented by spring security");
	}

}
