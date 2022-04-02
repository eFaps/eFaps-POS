/*
 * Copyright 2003 - 2022 The eFaps Team
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
package org.efaps.pos.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.efaps.pos.interfaces.ICreditNote;
import org.efaps.pos.interfaces.ICreditNoteItem;
import org.efaps.pos.util.Converter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = PosCreditNoteDto.Builder.class)
public class PosCreditNoteDto
    extends AbstractPayableDocumentDto
    implements ICreditNote
{

    private final DiscountDto discount;
    private final String sourceDocOid;

    public PosCreditNoteDto(final Builder _builder)
    {
        super(_builder);
        discount = _builder.discount;
        sourceDocOid = _builder.sourceDocOid;
    }

    public DiscountDto getDiscount()
    {
        return discount;
    }

    public String getSourceDocOid()
    {
        return sourceDocOid;
    }

    @Override
    public Set<ICreditNoteItem> getCreditNoteItems()
    {
        return getItems().stream().map(item -> (ICreditNoteItem) item).collect(Collectors.toSet());
    }

    @Override
    public ContactDto getContact()
    {
        return Converter.getContactDto(getContactOid());
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
        extends AbstractPayableDocumentDto.Builder<Builder, PosCreditNoteDto>
    {

        private DiscountDto discount;
        private String sourceDocOid;

        public Builder withDiscount(final DiscountDto _discount)
        {
            discount = _discount;
            return this;
        }

        public Builder withSourceDocOid(final String _sourceDocOid)
        {
            sourceDocOid = _sourceDocOid;
            return this;
        }

        public Builder withItems(final Set<PosDocItemDto> _items)
        {
            setItems(_items);
            return this;
        }

        public Builder withPayments(final Set<PaymentDto> _payments)
        {
            setPayments(_payments);
            return this;
        }

        @Override
        public PosCreditNoteDto build()
        {
            return new PosCreditNoteDto(this);
        }
    }
}
