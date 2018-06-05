/*
 * Copyright 2003 - 2018 The eFaps Team
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
package org.efaps.pos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.efaps.pos.dto.DocStatus;
import org.efaps.pos.entity.AbstractDocument.TaxEntry;
import org.efaps.pos.entity.Contact;
import org.efaps.pos.entity.Invoice;
import org.efaps.pos.entity.Order;
import org.efaps.pos.entity.Pos;
import org.efaps.pos.entity.Pos.Company;
import org.efaps.pos.entity.Receipt;
import org.efaps.pos.entity.Spot;
import org.efaps.pos.entity.Ticket;
import org.efaps.pos.entity.Workspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
@ActiveProfiles(profiles = "test")
public class DocumentServiceTest
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DocumentService documentService;

    @BeforeEach
    public void setup()
    {
        this.mongoTemplate.remove(new Query(), Order.class);
        this.mongoTemplate.remove(new Query(), Receipt.class);
        this.mongoTemplate.remove(new Query(), Ticket.class);
        this.mongoTemplate.remove(new Query(), Invoice.class);
        this.mongoTemplate.remove(new Query(), Workspace.class);
        this.mongoTemplate.remove(new Query(), Pos.class);
        this.mongoTemplate.remove(new Query(), Contact.class);
    }

    @Test
    public void testGetOrders()
    {
        this.mongoTemplate.save(new Order().setOid("1.1"));
        this.mongoTemplate.save(new Order().setOid("1.2"));
        this.mongoTemplate.save(new Order().setOid("1.3"));

        final List<Order> orders = this.documentService.getOrders();
        assertEquals(3, orders.size());
    }

    @Test
    public void testGetOrders4Spots()
    {
        this.mongoTemplate.save(new Order().setOid("1.1"));
        this.mongoTemplate.save(new Order().setOid("1.2").setSpot(new Spot()));
        this.mongoTemplate.save(new Order().setOid("1.3"));

        final Collection<Order> orders = this.documentService.getOrders4Spots();
        assertEquals(1, orders.size());
        assertEquals("1.2", orders.iterator().next().getOid());
    }

    @Test
    public void testCreateOrder()
    {
        final Order newOrder = new Order();
        newOrder.setNetTotal(new BigDecimal("123.45869"));
        newOrder.setTaxes(Collections.emptySet());

        final Order order = this.documentService.createOrder(newOrder);
        assertNotNull(order.getNumber());
        assertNotNull(order.getId());
        assertEquals(new BigDecimal("123.46"), order.getNetTotal());
        assertEquals(new BigDecimal("123.46"), order.getCrossTotal());
        assertEquals(1, this.mongoTemplate.findAll(Order.class).size());
    }

    @Test
    public void testUpdateOrder()
    {
        this.mongoTemplate.save(new Order());
        final Order updateOrder = this.mongoTemplate.findAll(Order.class).get(0);
        updateOrder.setNetTotal(new BigDecimal("123.45869"));
        updateOrder.setTaxes(Collections.singleton(new TaxEntry().setAmount(new BigDecimal(100))));

        final Order order = this.documentService.updateOrder(updateOrder);
        assertNotNull(order.getId());
        assertEquals(new BigDecimal("123.46"), order.getNetTotal());
        assertEquals(new BigDecimal("223.46"), order.getCrossTotal());
        assertEquals(1, this.mongoTemplate.findAll(Order.class).size());
    }

    @Test
    public void testDeleteOrderSetCanceled()
    {
        this.mongoTemplate.save(new Order().setStatus(DocStatus.OPEN));
        final Order order = this.mongoTemplate.findAll(Order.class).get(0);

        this.documentService.deleteOrder(order.getId());
        assertEquals(1, this.mongoTemplate.findAll(Order.class).size());
        final Order deleted = this.mongoTemplate.findById(order.getId(), Order.class);
        assertEquals(DocStatus.CANCELED, deleted.getStatus());
    }

    @Test
    public void testDeleteOrderWrongStatus()
    {
        this.mongoTemplate.save(new Order().setStatus(DocStatus.CLOSED));
        final Order order = this.mongoTemplate.findAll(Order.class).get(0);

        this.documentService.deleteOrder(order.getId());
        assertEquals(1, this.mongoTemplate.findAll(Order.class).size());
    }

    @Test
    public void testDeleteOrderNotFound()
    {
        this.mongoTemplate.save(new Order().setStatus(DocStatus.CLOSED));
        this.documentService.deleteOrder("a different id");
        assertEquals(1, this.mongoTemplate.findAll(Order.class).size());
    }

    @Test
    public void testCreateReceipt()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Receipt newReceipt = new Receipt()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet())
                        .setDate(LocalDate.now());

        final Receipt receipt = this.documentService.createReceipt(wsOid, newReceipt);
        assertNotNull(receipt.getNumber());
        assertNotNull(receipt.getId());
    }

    @Test
    public void testCreateReceiptCatchesError()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Receipt newReceipt = new Receipt()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet());

        final Receipt receipt = this.documentService.createReceipt(wsOid, newReceipt);
        assertNotNull(receipt.getNumber());
        assertNotNull(receipt.getId());
    }

    @Test
    public void testCreateInvoice()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Invoice invoice = new Invoice()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet())
                        .setDate(LocalDate.now());

        final Invoice createdInvoice = this.documentService.createInvoice(wsOid, invoice);
        assertNotNull(createdInvoice.getNumber());
        assertNotNull(createdInvoice.getId());
    }

    @Test
    public void testCreateInvoiceCatchesError()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Invoice invoice = new Invoice()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet());

        final Invoice createdInvoice = this.documentService.createInvoice(wsOid, invoice);
        assertNotNull(createdInvoice.getNumber());
        assertNotNull(createdInvoice.getId());
    }

    @Test
    public void testCreateTicket()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Ticket ticket = new Ticket()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet())
                        .setDate(LocalDate.now());

        final Ticket createdTicket = this.documentService.createTicket(wsOid, ticket);
        assertNotNull(createdTicket.getNumber());
        assertNotNull(createdTicket.getId());
    }

    @Test
    public void testCreateTicketCatchesError()
    {
        final String wsOid = "123.4";
        final String posOid = "223.4";
        final String contactOid = "323.4";
        this.mongoTemplate.save(new Workspace().setOid(wsOid).setPosOid(posOid));
        this.mongoTemplate.save(new Pos().setOid(posOid)
                        .setDefaultContactOid(contactOid)
                        .setCompany(new Company()
                                        .setName("company")
                                        .setTaxNumber("12345678911")));
        this.mongoTemplate.save(new Contact().setOid(contactOid));

        final Ticket ticket = new Ticket()
                        .setContactOid("1123.1")
                        .setNetTotal(new BigDecimal("123.45869"))
                        .setTaxes(Collections.emptySet());

        final Ticket createdTicket = this.documentService.createTicket(wsOid, ticket);
        assertNotNull(createdTicket.getNumber());
        assertNotNull(createdTicket.getId());
    }
}