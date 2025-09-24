package com.appsdeveloperblog.app.ws.io.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.appsdeveloperblog.app.ws.io.entity.ProviderEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
	ProviderEntity findByProviderId(String providerId);
}
