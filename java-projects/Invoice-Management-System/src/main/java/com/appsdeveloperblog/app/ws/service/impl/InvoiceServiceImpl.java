package com.appsdeveloperblog.app.ws.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.Repository.InvoiceRepository;
import com.appsdeveloperblog.app.ws.io.Repository.ProviderRepository;
import com.appsdeveloperblog.app.ws.io.entity.InvoiceEntity;
import com.appsdeveloperblog.app.ws.io.entity.InvoiceLineEntity;
import com.appsdeveloperblog.app.ws.io.entity.ProviderEntity;
import com.appsdeveloperblog.app.ws.service.InvoiceService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.InvoiceDto;
import com.appsdeveloperblog.app.ws.shared.dto.InvoiceLineDto;
import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;
import com.appsdeveloperblog.app.ws.ui.model.request.InvoiceLineDetailsRequestModel;

import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	ProviderRepository providerRepository;

	@Autowired
	Utils utils;

	ModelMapper mapper = new ModelMapper();

	@Override
	public InvoiceDto createInvoice(InvoiceDto dto) {
		ProviderEntity provider = providerRepository.findByProviderId(dto.getProviderName());
		if (provider == null)
			throw new RuntimeException("Provider not found");

		InvoiceEntity invoice = new InvoiceEntity();
		invoice.setInvoiceId(utils.generateUserId(30));
		invoice.setDeliveredBy(dto.getDeliveredBy());
		invoice.setPaid(dto.getPaid());
		invoice.setDateTime(LocalDateTime.now());
		invoice.setProviderName(provider.getName());
		invoice.setAddress(provider.getAddress());

		double total = 0;
		List<InvoiceLineEntity> lineEntities = new ArrayList<>();

		for (InvoiceLineDto lineDto : dto.getInvoiceLines()) {
			InvoiceLineEntity line = new InvoiceLineEntity();

			line.setInvoiceLineId(utils.generateUserId(30));
			line.setProductName(lineDto.getProductName());
			line.setQuantity(lineDto.getQuantity());
			line.setPrice(lineDto.getPrice());

			double value = lineDto.getPrice() * lineDto.getQuantity();
			line.setLineValue(value); // or line.calculateValue(); if it's a method that sets it internally
			line.setInvoice(invoice);

			total += value;
			lineEntities.add(line);
		}

		invoice.setTotal(total);
		invoice.setRemaining(total - dto.getPaid());
		invoice.setInvoiceLines(lineEntities);

		InvoiceEntity storedInvoiceDetails = invoiceRepository.save(invoice);
		ModelMapper modelMapper = new ModelMapper();
		InvoiceDto returnValue = modelMapper.map(storedInvoiceDetails, InvoiceDto.class);

		return returnValue;
	}

	@Override
	public InvoiceDto getInvoiceById(String invoiceId) {

		InvoiceDto returnValue;
		InvoiceEntity invoiceEntity = invoiceRepository.findByInvoiceId(invoiceId);

		if (invoiceEntity == null)
			throw new UsernameNotFoundException(invoiceId);

		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(invoiceEntity, InvoiceDto.class);

		return returnValue;
	}

	@Override
	public List<InvoiceDto> getInvoicesByProvider(String providerName) {
		List<InvoiceEntity> invoices = invoiceRepository.findByProviderName(providerName);
		List<InvoiceDto> result = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();
		for (InvoiceEntity entity : invoices) {
			InvoiceDto dto = modelMapper.map(entity, InvoiceDto.class);
			result.add(dto);
		}

		return result;
	}

	@Override
	public List<InvoiceDto> getInvoicesByDate(String date) {
		List<InvoiceDto> returnList = new ArrayList<>();
		Iterable<InvoiceEntity> invoices = invoiceRepository.findAll();

		LocalDate targetDate = LocalDate.parse(date);

		for (InvoiceEntity invoice : invoices) {
			if (invoice.getDateTime().toLocalDate().equals(targetDate)) {
				InvoiceDto dto = new ModelMapper().map(invoice, InvoiceDto.class);
				returnList.add(dto);
			}
		}

		return returnList;
	}

	@Override
	public InvoiceLineDto getInvoiceLineById(String invoiceId, String lineId) {
		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(invoiceId);
		if (invoice == null)
			throw new RuntimeException("Invoice not found");

		for (InvoiceLineEntity line : invoice.getInvoiceLines()) {
			if (line.getInvoiceLineId().equals(lineId)) {
				return new ModelMapper().map(line, InvoiceLineDto.class);
			}
		}

		throw new RuntimeException("Invoice line not found in the specified invoice");
	}

	@Override
	@Transactional
	public InvoiceDto updateInvoice(String invoiceId, InvoiceDto dto) {
		ModelMapper modelMapper = new ModelMapper();
		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(invoiceId);
		if (invoice == null)
			throw new RuntimeException("Invoice not found");

		invoice.setDeliveredBy(dto.getDeliveredBy());
		invoice.setDateTime(dto.getDateTime() != null ? dto.getDateTime() : LocalDateTime.now());
		invoice.setPaid(dto.getPaid());

		double total = 0;
		for (InvoiceLineEntity line : invoice.getInvoiceLines()) {
			total += line.getPrice() * line.getQuantity();
		}

		invoice.setTotal(total);
		invoice.setRemaining(total - dto.getPaid());

		if (dto.getProviderName() != null) {
			ProviderEntity provider = providerRepository.findByProviderId(dto.getProviderName());
			if (provider != null) {
				invoice.setProviderName(provider.getName());
				invoice.setAddress(provider.getAddress());
			}
		}

		InvoiceEntity updated = invoiceRepository.save(invoice);
		return modelMapper.map(updated, InvoiceDto.class);
	}

	@Override
	@Transactional
	public InvoiceLineDto updateInvoiceLine(String invoiceId, String lineId,
			InvoiceLineDetailsRequestModel updateModel) {
		ModelMapper modelMapper = new ModelMapper();
		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(invoiceId);
		if (invoice == null)
			throw new RuntimeException("Invoice not found");

		InvoiceLineEntity targetLine = null;

		// Find the invoice line using a for loop
		for (InvoiceLineEntity line : invoice.getInvoiceLines()) {
			if (line.getInvoiceLineId().equals(lineId)) {
				targetLine = line;
				break;
			}
		}

		if (targetLine == null) {
			throw new RuntimeException("Invoice line not found");
		}

		targetLine.setProductName(updateModel.getProductName());
		targetLine.setPrice(updateModel.getPrice());
		targetLine.setQuantity(updateModel.getQuantity());

		double newValue = targetLine.getPrice() * targetLine.getQuantity();
		targetLine.setLineValue(newValue);

		double total = 0;
		for (InvoiceLineEntity line : invoice.getInvoiceLines()) {
			total += line.getLineValue();
		}

		invoice.setTotal(total);
		invoice.setRemaining(total - invoice.getPaid());

		invoiceRepository.save(invoice);

		return modelMapper.map(targetLine, InvoiceLineDto.class);
	}

	@Override
	@Transactional
	public void deleteInvoice(String invoiceId) {
		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(invoiceId);
		if (invoice == null) {
			throw new RuntimeException("Invoice not found");
		}
		invoiceRepository.delete(invoice);
	}

	@Override
	@Transactional
	public void deleteInvoiceLine(String invoiceId, String lineId) {

		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(invoiceId);
		if (invoice == null) {
			throw new RuntimeException("Invoice not found");
		}

		Iterator<InvoiceLineEntity> iterator = invoice.getInvoiceLines().iterator();
		boolean removed = false;

		while (iterator.hasNext()) {
			InvoiceLineEntity line = iterator.next();
			if (line.getInvoiceLineId().equals(lineId)) {
				iterator.remove();
				removed = true;
				break;
			}
		}

		if (!removed) {
			throw new RuntimeException("Invoice line not found");
		}

		double total = 0;
		for (InvoiceLineEntity line : invoice.getInvoiceLines()) {
			total += line.getLineValue();
		}

		invoice.setTotal(total);
		invoice.setRemaining(total - invoice.getPaid());

		invoiceRepository.save(invoice);
	}

}
