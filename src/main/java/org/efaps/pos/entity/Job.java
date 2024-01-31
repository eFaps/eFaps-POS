/*
 * Copyright © 2003 - 2024 The eFaps Team (-)
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
 */
package org.efaps.pos.entity;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.efaps.pos.entity.AbstractDocument.Item;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job
{

    @Id
    private String id;

    private String documentId;

    private String printerOid;

    private String reportOid;

    private List<Item> items;

    private String shoutout;

    @CreatedBy
    private String user;

    @CreatedDate
    private Instant createdDate;

    @Version
    private long version;

    public String getId()
    {
        return id;
    }

    public Job setId(final String _id)
    {
        id = _id;
        return this;
    }

    public String getPrinterOid()
    {
        return printerOid;
    }

    public Job setPrinterOid(final String _printerOid)
    {
        printerOid = _printerOid;
        return this;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public Job setItems(final List<Item> _items)
    {
        items = _items;
        return this;
    }

    public String getDocumentId()
    {
        return documentId;
    }

    public Job setDocumentId(final String _documentId)
    {
        documentId = _documentId;
        return this;
    }

    public String getReportOid()
    {
        return reportOid;
    }

    public Job setReportOid(final String _reportOid)
    {
        reportOid = _reportOid;
        return this;
    }

    public String getShoutout()
    {
        return shoutout;
    }

    public Job setShoutout(final String shoutout)
    {
        this.shoutout = shoutout;
        return this;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(final String user)
    {
        this.user = user;
    }

    public Instant getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(final Instant createdDate)
    {
        this.createdDate = createdDate;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this);
    }
}
