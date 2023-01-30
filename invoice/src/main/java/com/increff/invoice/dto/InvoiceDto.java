package com.increff.invoice.dto;

import com.increff.invoice.api.InvoiceApi;
import com.increff.invoice.model.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@Service
public class InvoiceDto {

    @Autowired
    private InvoiceApi api;

    public ResponseEntity<byte[]> generateInvoice(InvoiceForm form) throws IOException {
        return api.generateInvoice(form);
    }
}