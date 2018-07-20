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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product
{

    @Id
    private String id;
    private String oid;
    private String sku;
    private String description;
    private String imageOid;
    private BigDecimal netPrice;
    private BigDecimal crossPrice;
    private Set<String> categoryOids;
    private Set<Tax> taxes;
    private String uoM;
    private String uoMCode;
    private Set<ProductRelation> relations;

    public String getOid()
    {
        return this.oid;
    }

    public Product setOid(final String _oid)
    {
        this.oid = _oid;
        this.id = _oid;
        return this;
    }

    public String getSKU()
    {
        return this.sku;
    }

    public Product setSKU(final String _sku)
    {
        this.sku = _sku;
        return this;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Product setDescription(final String _description)
    {
        this.description = _description;
        return this;
    }

    public String getImageOid()
    {
        return this.imageOid;
    }

    public Product setImageOid(final String _imageOid)
    {
        this.imageOid = _imageOid;
        return this;
    }

    public BigDecimal getNetPrice()
    {
        return this.netPrice;
    }

    public Product setNetPrice(final BigDecimal _netPrice)
    {
        this.netPrice = _netPrice;
        return this;
    }

    public BigDecimal getCrossPrice()
    {
        return this.crossPrice;
    }

    public Product setCrossPrice(final BigDecimal _crossPrice)
    {
        this.crossPrice = _crossPrice;
        return this;
    }

    public Set<String> getCategoryOids()
    {
        return this.categoryOids == null ? Collections.emptySet() : this.categoryOids;
    }

    public Product setCategoryOids(final Set<String> _categoryOids)
    {
        this.categoryOids = _categoryOids;
        return this;
    }

    public Set<Tax> getTaxes()
    {
        return this.taxes;
    }

    public Product setTaxes(final Set<Tax> _taxes)
    {
        this.taxes = _taxes;
        return this;
    }

    public String getUoM()
    {
        return this.uoM;
    }

    public Product setUoM(final String _uoM)
    {
        this.uoM = _uoM;
        return this;
    }

    public String getUoMCode()
    {
        return this.uoMCode;
    }

    public Product setUoMCode(final String _uoMCode)
    {
        this.uoMCode = _uoMCode;
        return this;
    }

    public Set<ProductRelation> getRelations()
    {
        return this.relations;
    }

    public Product setRelations(final Set<ProductRelation> _relations)
    {
        this.relations = _relations;
        return this;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this);
    }

}
