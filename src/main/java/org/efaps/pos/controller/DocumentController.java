/*
 * Copyright 2003 - 2019 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.efaps.pos.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.efaps.pos.config.IApi;
import org.efaps.pos.dto.DocStatus;
import org.efaps.pos.dto.PayableHeadDto;
import org.efaps.pos.dto.PosInvoiceDto;
import org.efaps.pos.dto.PosOrderDto;
import org.efaps.pos.dto.PosReceiptDto;
import org.efaps.pos.dto.PosTicketDto;
import org.efaps.pos.entity.Invoice;
import org.efaps.pos.entity.Order;
import org.efaps.pos.entity.Receipt;
import org.efaps.pos.entity.Ticket;
import org.efaps.pos.projection.PayableHead;
import org.efaps.pos.service.DocumentService;
import org.efaps.pos.util.Converter;
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

@RestController
@RequestMapping(IApi.BASEPATH)
public class DocumentController
{

    private final DocumentService documentService;

    public DocumentController(final DocumentService _service)
    {
        documentService = _service;
    }

    @PostMapping(path = "workspaces/{oid}/documents/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosReceiptDto createReceipt(@PathVariable("oid") final String _oid,
                                       @RequestBody final PosReceiptDto _receiptDto)
    {
        return Converter.toDto(documentService.createReceipt(_oid, Converter.toEntity(_receiptDto)));
    }

    @PostMapping(path = "workspaces/{oid}/documents/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosInvoiceDto createInvoice(@PathVariable("oid") final String _oid,
                                       @RequestBody final PosInvoiceDto _invoiceDto)
    {
        return Converter.toDto(documentService.createInvoice(_oid, Converter.toEntity(_invoiceDto)));
    }

    @PostMapping(path = "workspaces/{oid}/documents/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosTicketDto createTicket(@PathVariable("oid") final String _oid,
                                     @RequestBody final PosTicketDto _ticketDto)
    {
        return Converter.toDto(documentService.createTicket(_oid, Converter.toEntity(_ticketDto)));
    }

    @PostMapping(path = "documents/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosOrderDto createOrder(@RequestBody final PosOrderDto _orderDto)
    {
        return Converter.toDto(documentService.createOrder(Converter.toEntity(_orderDto)));
    }

    @PutMapping(path = "documents/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosOrderDto updateOrder(@PathVariable(name = "orderId") final String _orderId,
                                   @RequestBody final PosOrderDto _orderDto)
    {
        return Converter.toDto(documentService.updateOrder(Converter.toEntity(_orderDto).setId(_orderId)));
    }

    @DeleteMapping(path = "documents/orders/{orderId}")
    public void deleteOrder(@PathVariable(name = "orderId") final String _orderId)
    {
        documentService.deleteOrder(_orderId);
    }

    @GetMapping(path = "documents/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PosOrderDto> getOrders(@RequestParam(name = "spot", required = false) final boolean _spots)
    {
        final Collection<Order> orders = _spots
                        ? documentService.getOrders4Spots()
                        : documentService.getOrders();
        return orders.stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/orders", produces = MediaType.APPLICATION_JSON_VALUE, params = { "status" })
    public List<PosOrderDto> getOrders(@RequestParam(name = "status") final DocStatus _status)
    {
        return documentService.getOrders(_status).stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/orders", produces = MediaType.APPLICATION_JSON_VALUE, params = { "term" })
    public List<PosOrderDto> findOrders(@RequestParam(name = "term") final String _term) {
        return documentService.findOrders(_term).stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PosReceiptDto> getReceipts4Balance(@RequestParam(name = "balanceOid") final String _balanceOid)
    {
        final Collection<Receipt> receipts = documentService.getReceipts4Balance(_balanceOid);
        return receipts.stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/receipts", produces = MediaType.APPLICATION_JSON_VALUE, params = { "term" })
    public List<PayableHeadDto> findReceipts(@RequestParam(name = "term") final String _term)
    {
        final Collection<PayableHead> receipts = documentService.findReceipts(_term);
        return receipts.stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/receipts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosReceiptDto getReceipt(@PathVariable("id") final String _id)
    {
        final Receipt receipt = documentService.getReceipt(_id);
        return Converter.toDto(receipt);
    }

    @GetMapping(path = "documents/invoices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosInvoiceDto getInvoice(@PathVariable("id") final String _id)
    {
        final Invoice invoice = documentService.getInvoice(_id);
        return Converter.toDto(invoice);
    }


    @GetMapping(path = "documents/tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PosTicketDto getTicket(@PathVariable("id") final String _id)
    {
        final Ticket ticket = documentService.getTicket(_id);
        return Converter.toDto(ticket);
    }

    @GetMapping(path = "documents/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PosInvoiceDto> getInvoices4Balance(@RequestParam(name = "balanceOid") final String _balanceOid)
    {
        final Collection<Invoice> receipts = documentService.getInvoices4Balance(_balanceOid);
        return receipts.stream()
                        .map(receipt -> Converter.toDto(receipt))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/invoices", produces = MediaType.APPLICATION_JSON_VALUE, params = { "term" })
    public List<PayableHeadDto> findInvoices(@RequestParam(name = "term") final String _term)
    {
        final Collection<PayableHead> invoices = documentService.findInvoices(_term);
        return invoices.stream()
                        .map(invoice -> Converter.toDto(invoice))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PosTicketDto> getTickets4Balance(@RequestParam(name = "balanceOid") final String _balanceOid)
    {
        final Collection<Ticket> receipts = documentService.getTickets4Balance(_balanceOid);
        return receipts.stream()
                        .map(_order -> Converter.toDto(_order))
                        .collect(Collectors.toList());
    }

    @GetMapping(path = "documents/tickets", produces = MediaType.APPLICATION_JSON_VALUE, params = { "term" })
    public List<PayableHeadDto> findTickets(@RequestParam(name = "term") final String _term)
    {
        final Collection<PayableHead> tickets = documentService.findTickets(_term);
        return tickets.stream()
                        .map(ticket -> Converter.toDto(ticket))
                        .collect(Collectors.toList());
    }

}
