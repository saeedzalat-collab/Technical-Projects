package com.appsdeveloperblog.app.ws.io.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appsdeveloperblog.app.ws.io.entity.InvoiceEntity;

public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long> {

	 InvoiceEntity findByInvoiceId(String invoiceId);
	
	 List<InvoiceEntity> findByProviderName(String providerName);

	 List<InvoiceEntity> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
