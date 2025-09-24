package com.appsdeveloperblog.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.Repository.ProviderRepository;
import com.appsdeveloperblog.app.ws.io.entity.ProviderEntity;

import com.appsdeveloperblog.app.ws.service.ProviderService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;


@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired 
    ProviderRepository repo;
	@Autowired
	Utils utils;
	
    ModelMapper mapper = new ModelMapper();
    

	@Override
	public ProviderDto createProvider(ProviderDto provider) {
		
		//BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper = new ModelMapper();
		ProviderEntity providerEntity = modelMapper.map(provider, ProviderEntity.class);
		
		String publicProviderId = utils.generateUserId(30);
		providerEntity.setProviderId(publicProviderId);
		
		ProviderEntity storedProviderDetails = repo.save(providerEntity);

		ProviderDto returnValue = modelMapper.map(storedProviderDetails, ProviderDto.class);
        return returnValue;
	}

	@Override
	public ProviderDto getProviderByProviderId(String providerId) {
		
		
		ProviderDto returnValue;
		ProviderEntity providerEntity = repo.findByProviderId(providerId);
		
		if(providerEntity == null) throw new UsernameNotFoundException(providerId);
		
		ModelMapper modelMapper = new ModelMapper();
	    returnValue = modelMapper.map(providerEntity, ProviderDto.class);
		
		return returnValue;
	}


	@Override
	public List<ProviderDto> getAllProviders(int page, int limit) {
		List<ProviderDto> returnValue = new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit); 
		Page<ProviderEntity> providersPage = repo.findAll(pageableRequest);
		List<ProviderEntity> providers = providersPage.getContent();
		
		for(ProviderEntity providerEntity : providers)
		{
			ProviderDto providerDto = new ProviderDto();
			BeanUtils.copyProperties(providerEntity, providerDto);
			returnValue.add(providerDto);
		}
		
		return returnValue;
	}


	@Override
	public ProviderDto updateProvider(String id, ProviderDto providerDto) {
		
		ProviderDto returnValue = new ProviderDto();
		ProviderEntity providerEntity = repo.findByProviderId(id);
		
		if(providerEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		providerEntity.setNote(providerDto.getNote());
		providerEntity.setAddress(providerDto.getAddress());
		providerEntity.setPhone(providerDto.getPhone());
		
		ProviderEntity updatedProviderDetails = repo.save(providerEntity);
		returnValue = mapper.map(updatedProviderDetails, ProviderDto.class);
	//	BeanUtils.copyProperties(updatedProviderDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public void deleteProvider(String id) {
		ProviderEntity providerEntity = repo.findByProviderId(id);
		
		if(providerEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		repo.delete(providerEntity);
		
	}

	

}
