package com.appsdeveloperblog.app.ws.ui.controller;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.ProviderService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.ProviderDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.AddressesRest;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.ProviderRest;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationName;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("users") // http://localhost:8080/users
public class userController {

	@Autowired
	UserService userService;
	@Autowired
	AddressService addressService;
	@Autowired
	AddressService addressesService;
    @Autowired 
    ProviderService providerService;
    
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		// BeanUtils.copyProperties(userDto, returnValue);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userDto, UserRest.class);

		return returnValue;
	}
	
	// http://localhost:8088/mobile-app-ws/users/email-verification?token=sdfsdf
	@GetMapping(path = "/email-verification",
	            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	//@CrossOrigin(origins="*") //allows all the end points web services to access this method
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
	    OperationStatusModel returnValue = new OperationStatusModel();
	    returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

	    boolean isVerified = true;

	    if (isVerified) {
	        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	    } else {
	        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
	    }

	    return returnValue;
	}


	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, // accept xml or json
																									// request
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) // respond back with json
																								// or xml
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		// UserDto userDto = new UserDto();
		// BeanUtils.copyProperties(userDetails, userDto);

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		userDto.setRoles(new HashSet(Arrays.asList("ROLE_USER")));

		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping(path = "/providers",consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE })
  //  @PreAuthorize("hasAnyRole('ADMIN', 'CHEF_ACCOUNTANT')")
    public ProviderRest addProvider(@RequestBody ProviderDetailsRequestModel providerDetails) {
		
		ProviderRest returnValue = new ProviderRest();
		
		ModelMapper modelMapper = new ModelMapper();
		ProviderDto providerDto = modelMapper.map(providerDetails, ProviderDto.class);
		//userDto.setRoles(new HashSet(Arrays.asList("ROLE_USER")));

		ProviderDto createdProvider = providerService.createProvider(providerDto);
		returnValue = modelMapper.map(createdProvider, ProviderRest.class);

		return returnValue;
    }
	
	@GetMapping(path = "/providers/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	public ProviderRest getProvider(@PathVariable String id)
	{
		ProviderRest returnValue = new ProviderRest();

		ProviderDto providerDto = providerService.getProviderByProviderId(id);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(providerDto, ProviderRest.class);

		return returnValue;
		
	}
	
	@GetMapping(path = "/providers", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) // respond back with // json or xml)
	public List<ProviderRest> getProviders(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		
		List<ProviderRest> returnValue = new ArrayList<>();
		List<ProviderDto> providers = providerService.getAllProviders(page, limit);

		for (ProviderDto providerDto : providers) {
			ProviderRest providerModel = new ProviderRest();
			BeanUtils.copyProperties(providerDto, providerModel);
			returnValue.add(providerModel);
		}
		return returnValue;
	}
	
	@PutMapping(path = "/providers/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ProviderRest updateProvider(@PathVariable String id, @RequestBody ProviderDetailsRequestModel providerDetails) {
		ProviderRest returnValue = new ProviderRest();

		ProviderDto providerDto = new ProviderDto();
		providerDto = new ModelMapper().map(providerDetails, ProviderDto.class);
		
		ProviderDto updatedProvider = providerService.updateProvider(id, providerDto);
		returnValue = new ModelMapper().map(updatedProvider, ProviderRest.class);

		return returnValue;
	}
	
	@DeleteMapping(path = "/providers/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) 
	public OperationStatusModel deleteProvider(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		providerService.deleteProvider(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		userDto = new ModelMapper().map(userDetails, UserDto.class);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		returnValue = new ModelMapper().map(updatedUser, UserRest.class);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) 
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@Operation(
		    summary = "Get Users",
		    description = "Requires Bearer JWT Token",
		    security = @SecurityRequirement(name = "BearerAuth")
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Successful Operation"),
		    @ApiResponse(responseCode = "401", description = "Unauthorized")
		})
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) // respond back with // json or xml)
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}

	// http://localhost:8080/mobile-app-ws/users/jfjjfjf/adresses
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
		
		List<AddressesRest> returnValue = new ArrayList<>();
		List<AddressDTO> addressesDTO = addressesService.getAddresses(id);

		if (addressesDTO != null && !addressesDTO.isEmpty()) {

			ModelMapper modelMapper = new ModelMapper();

			Type listType = new TypeToken<List<AddressesRest>>() {
			}.getType();
			returnValue = modelMapper.map(addressesDTO, listType);

		}
		
		Link userLink = WebMvcLinkBuilder.linkTo(userController.class).slash(id).withRel("user");
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddresses(id))
				//.slash(id)
				//.slash("addresses")
				//.slash(addressId)
				.withSelfRel();
		return CollectionModel.of(returnValue, userLink, selfLink);
	}

	// http://localhost:8080/mobile-app-ws/users/jfjjfjf/adresses/{jhjhjjh}
	@GetMapping(path= "/{id}/addresses/{addressId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<AddressesRest> getUserAddress(@PathVariable String id, @PathVariable String addressId)
	{
		AddressDTO addressDTO = addressService.getAddress(addressId);
		
		ModelMapper modelMapper = new ModelMapper();
		AddressesRest returnValue = modelMapper.map(addressDTO, AddressesRest.class);
		
		Link userLink = WebMvcLinkBuilder.linkTo(userController.class).slash(id).withRel("user");
		Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddresses(id))
				//.slash(id)
				//.slash("addresses")
				.withRel("addresses");
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddress(id, addressId))
				//.slash(id)
				//.slash("addresses")
				//.slash(addressId)
				.withSelfRel();
		
		//returnValue.add(userLink);
		//returnValue.add(userAddressesLink);
		//returnValue.add(selfLink);
		
		
		
		return EntityModel.of(returnValue, List.of(userLink, userAddressesLink, selfLink));
	}

}
