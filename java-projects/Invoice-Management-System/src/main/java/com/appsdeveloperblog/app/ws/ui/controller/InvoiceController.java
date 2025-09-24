package com.appsdeveloperblog.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.InvoiceDto;
import com.appsdeveloperblog.app.ws.shared.dto.InvoiceLineDto;
import com.appsdeveloperblog.app.ws.shared.dto.ProviderDto;
import com.appsdeveloperblog.app.ws.ui.model.request.InvoiceDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.InvoiceLineDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.InvoiceLineRest;
import com.appsdeveloperblog.app.ws.ui.model.response.InvoiceRest;
import com.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.ProviderRest;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationName;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.service.InvoiceService;


@RestController 
@RequestMapping("invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE })
   // @PreAuthorize("hasAnyRole('ADMIN', 'CHEF_ACCOUNTANT', 'ACCOUNTANT')")
    public InvoiceRest addInvoice(@RequestBody InvoiceDetailsRequestModel invoiceDetails) {
		
		InvoiceRest returnValue = new InvoiceRest();
		
		ModelMapper modelMapper = new ModelMapper();
		InvoiceDto invoiceDto = modelMapper.map(invoiceDetails, InvoiceDto.class);
		//userDto.setRoles(new HashSet(Arrays.asList("ROLE_USER")));

		InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
		returnValue = modelMapper.map(createdInvoice, InvoiceRest.class);

		return returnValue;
    }

	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	public InvoiceRest getInvoiceById(@PathVariable String id)
	{
		InvoiceRest returnValue = new InvoiceRest();

		InvoiceDto invoiceDto = invoiceService.getInvoiceById(id);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(invoiceDto, InvoiceRest.class);

		return returnValue;
		
	}
	
	@GetMapping(path = "/provider/{providerName}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<InvoiceRest> getInvoicesByProvider(@PathVariable String providerName) {
	    List<InvoiceRest> returnList = new ArrayList<>();
	    
	    List<InvoiceDto> invoiceDtos = invoiceService.getInvoicesByProvider(providerName);
	    ModelMapper modelMapper = new ModelMapper();

	    for (InvoiceDto dto : invoiceDtos) {
	        InvoiceRest invoiceRest = modelMapper.map(dto, InvoiceRest.class);
	        returnList.add(invoiceRest);
	    }

	    return returnList;
	}
	
	@GetMapping(path = "/date/{date}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<InvoiceRest> getInvoicesByDate(@PathVariable String date) {
	    List<InvoiceRest> returnList = new ArrayList<>();

	    List<InvoiceDto> invoiceDtos = invoiceService.getInvoicesByDate(date);
	    ModelMapper modelMapper = new ModelMapper();

	    for (InvoiceDto dto : invoiceDtos) {
	        InvoiceRest rest = modelMapper.map(dto, InvoiceRest.class);
	        returnList.add(rest);
	    }

	    return returnList;
	}
	
	@GetMapping("/{invoiceId}/lines/{lineId}")
	public InvoiceLineRest getInvoiceLineById(@PathVariable String invoiceId, @PathVariable String lineId) {
	    InvoiceLineDto dto = invoiceService.getInvoiceLineById(invoiceId, lineId);
	    return new ModelMapper().map(dto, InvoiceLineRest.class);
	}


	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceRest updateInvoice(@PathVariable String id, @RequestBody InvoiceDetailsRequestModel updatedInvoice) {
		ModelMapper modelMapper = new ModelMapper();
	    InvoiceDto invoiceDto = modelMapper.map(updatedInvoice, InvoiceDto.class);
	    InvoiceDto updatedDto = invoiceService.updateInvoice(id, invoiceDto);
	    return modelMapper.map(updatedDto, InvoiceRest.class);
	}
	
	@PutMapping(path = "/{invoiceId}/lines/{lineId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceLineRest updateInvoiceLine(@PathVariable String invoiceId,
	                                         @PathVariable String lineId,
	                                         @RequestBody InvoiceLineDetailsRequestModel updateModel) {
		ModelMapper modelMapper = new ModelMapper();
	    InvoiceLineDto updated = invoiceService.updateInvoiceLine(invoiceId, lineId, updateModel);
	    return modelMapper.map(updated, InvoiceLineRest.class);
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteInvoice(@PathVariable String id) {
	  
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		invoiceService.deleteInvoice(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@DeleteMapping("/{invoiceId}/lines/{lineId}")
	public OperationStatusModel deleteInvoiceLine(@PathVariable String invoiceId, @PathVariable String lineId) {
	    
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		invoiceService.deleteInvoiceLine(invoiceId, lineId);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}







}