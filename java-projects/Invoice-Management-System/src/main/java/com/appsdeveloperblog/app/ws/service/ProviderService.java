package com.appsdeveloperblog.app.ws.service;

import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;



public interface ProviderService {
    ProviderDto createProvider(ProviderDto provider);
    ProviderDto getProviderByProviderId(String providerId);
    List<ProviderDto> getAllProviders(int page, int limit);
    ProviderDto updateProvider(String id, ProviderDto providerDto);
    void deleteProvider(String id);
}
