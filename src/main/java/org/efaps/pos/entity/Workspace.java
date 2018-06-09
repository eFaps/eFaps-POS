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

package org.efaps.pos.entity;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.efaps.pos.dto.DocType;
import org.efaps.pos.dto.SpotConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workspaces")
public class Workspace
{

    @Id
    private String id;

    private String oid;

    private String name;

    private String posOid;

    private Set<DocType> docTypes;

    private SpotConfig spotConfig;

    private String warehouseOid;

    public String getOid()
    {
        return this.oid;
    }

    public Workspace setOid(final String _oid)
    {
        this.oid = _oid;
        this.id = _oid;
        return this;
    }

    public String getName()
    {
        return this.name;
    }

    public Workspace setName(final String _name)
    {
        this.name = _name;
        return this;
    }

    public String getPosOid()
    {
        return this.posOid;
    }

    public Workspace setPosOid(final String _posOid)
    {
        this.posOid = _posOid;
        return this;
    }

    public Set<DocType> getDocTypes()
    {
        return this.docTypes;
    }

    public Workspace setDocTypes(final Set<DocType> _docTypes)
    {
        this.docTypes = _docTypes;
        return this;
    }

    public SpotConfig getSpotConfig()
    {
        return this.spotConfig;
    }

    public Workspace setSpotConfig(final SpotConfig _spotConfig)
    {
        this.spotConfig = _spotConfig;
        return this;
    }

    public String getWarehouseOid()
    {
        return this.warehouseOid;
    }

    public Workspace setWarehouseOid(final String _warehouseOid)
    {
        this.warehouseOid = _warehouseOid;
        return this;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this);
    }
}
