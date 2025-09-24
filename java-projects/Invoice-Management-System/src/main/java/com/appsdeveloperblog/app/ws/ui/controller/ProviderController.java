package com.appsdeveloperblog.app.ws.ui.controller;


import java.util.ArrayList;
import java.util.List;
import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;
import com.appsdeveloperblog.app.ws.ui.model.request.ProviderDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.ProviderRest;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationName;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.appsdeveloperblog.app.ws.service.ProviderService;

@RestController 
@RequestMapping("providers")
public class ProviderController {
    @Autowired 
    ProviderService providerService;


	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
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
	
	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	public ProviderRest getProvider(@PathVariable String id)
	{
		ProviderRest returnValue = new ProviderRest();

		ProviderDto providerDto = providerService.getProviderByProviderId(id);
		ModelMapper modelMapper = new ModelMapper(); // rep
		returnValue = modelMapper.map(providerDto, ProviderRest.class);

		return returnValue;
		
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) // respond back with // json or xml)
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
	
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ProviderRest updateProvider(@PathVariable String id, @RequestBody ProviderDetailsRequestModel providerDetails) {
		ProviderRest returnValue = new ProviderRest();

		ProviderDto providerDto = new ProviderDto();
		providerDto = new ModelMapper().map(providerDetails, ProviderDto.class);
		
		ProviderDto updatedProvider = providerService.updateProvider(id, providerDto);
		returnValue = new ModelMapper().map(updatedProvider, ProviderRest.class);

		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) 
	public OperationStatusModel deleteProvider(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		providerService.deleteProvider(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
}
