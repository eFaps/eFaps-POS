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
package org.efaps.pos.util;

import java.util.stream.Collectors;

import org.efaps.pos.dto.CategoryDto;
import org.efaps.pos.dto.CompanyDto;
import org.efaps.pos.dto.PosDocItemDto;
import org.efaps.pos.dto.PosDto;
import org.efaps.pos.dto.PosOrderDto;
import org.efaps.pos.dto.PosReceiptDto;
import org.efaps.pos.dto.PosUserDto;
import org.efaps.pos.dto.ProductDto;
import org.efaps.pos.dto.TaxDto;
import org.efaps.pos.dto.TaxEntryDto;
import org.efaps.pos.dto.WorkspaceDto;
import org.efaps.pos.entity.AbstractDocument;
import org.efaps.pos.entity.AbstractDocument.TaxEntry;
import org.efaps.pos.entity.Category;
import org.efaps.pos.entity.Order;
import org.efaps.pos.entity.Pos;
import org.efaps.pos.entity.Pos.Company;
import org.efaps.pos.entity.Product;
import org.efaps.pos.entity.Receipt;
import org.efaps.pos.entity.Tax;
import org.efaps.pos.entity.User;
import org.efaps.pos.entity.Workspace;

public final class Converter
{

    private Converter()
    {
    }

    public static Receipt toEntity(final PosReceiptDto _dto)
    {
        final Receipt ret = new Receipt()
                        .setId(_dto.getId())
                        .setOid(_dto.getOid())
                        .setNumber(_dto.getNumber())
                        .setStatus(_dto.getStatus())
                        .setItems(_dto.getItems().stream()
                                        .map(_item -> Converter.toEntity((PosDocItemDto) _item))
                                        .collect(Collectors.toSet()));
        return ret;
    }

    public static Order toEntity(final PosOrderDto _dto)
    {
        final Order ret = new Order()
                        .setId(_dto.getId())
                        .setOid(_dto.getOid())
                        .setNumber(_dto.getNumber())
                        .setItems(_dto.getItems().stream()
                                        .map(_item -> Converter.toEntity((PosDocItemDto) _item))
                                        .collect(Collectors.toSet()))
                        .setStatus(_dto.getStatus());
        return ret;
    }

    public static AbstractDocument.Item toEntity(final PosDocItemDto _dto) {
        return new AbstractDocument.Item()
                        .setOid(_dto.getOid())
                        .setIndex(_dto.getIndex())
                        .setCrossPrice(_dto.getCrossPrice())
                        .setCrossUnitPrice(_dto.getCrossUnitPrice())
                        .setNetPrice(_dto.getNetPrice())
                        .setNetUnitPrice(_dto.getNetUnitPrice())
                        .setQuantity(_dto.getQuantity())
                        .setProductOid(_dto.getProduct() == null
                            ? _dto.getProductOid()
                            : _dto.getProduct().getOid())
                        .setTaxes(_dto.getTaxes() ==  null
                            ? null
                            : _dto.getTaxes().stream()
                                .map(_tax -> Converter.toEntity(_tax))
                                .collect(Collectors.toSet()));
    }

    public static Product toEntity(final ProductDto _dto)
    {
        final Product ret = new Product()
                        .setOid(_dto.getOid())
                        .setSKU(_dto.getSku())
                        .setImageOid(_dto.getImageOid())
                        .setDescription(_dto.getDescription())
                        .setNetPrice(_dto.getNetPrice())
                        .setCrossPrice(_dto.getCrossPrice())
                        .setCategoryOids(_dto.getCategoryOids())
                        .setTaxes(_dto.getTaxes().stream()
                                        .map(_tax -> toEntity(_tax))
                                        .collect(Collectors.toSet()));
        return ret;
    }

    public static ProductDto toDto(final Product _entity)
    {
        return ProductDto.builder()
                        .withSKU(_entity.getSKU())
                        .withDescription(_entity.getDescription())
                        .withImageOid(_entity.getImageOid())
                        .withOID(_entity.getOid())
                        .withNetPrice(_entity.getNetPrice())
                        .withCrossPrice(_entity.getCrossPrice())
                        .withCategoryOids(_entity.getCategoryOids())
                        .withTaxes(_entity.getTaxes() == null
                                ? null
                                :_entity.getTaxes().stream()
                                        .map(_tax -> toDto(_tax))
                                        .collect(Collectors.toSet()))
                        .build();
    }

    public static PosUserDto toDto(final User _user)
    {
        return PosUserDto.builder()
                        .withUsername(_user.getUsername())
                        .withFirstName(_user.getFirstName())
                        .withSurName(_user.getSurName())
                        .build();
    }

    public static WorkspaceDto toDto(final Workspace _entity)
    {
        return WorkspaceDto.builder()
                        .withOID(_entity.getOid())
                        .withName(_entity.getName())
                        .withPosOid(_entity.getPosOid())
                        .build();
    }

    public static PosDto toDto(final Pos _entity)
    {
        return PosDto.builder()
                        .withOID(_entity.getOid())
                        .withName(_entity.getName())
                        .withCompany(_entity.getCompany() == null
                            ? null
                            : CompanyDto.builder()
                                        .withName(_entity.getCompany().getName())
                                        .withTaxNumber(_entity.getCompany().getTaxNumber())
                                        .build())
                        .build();
    }

    public static Pos toEntity(final PosDto _dto)
    {
        final Pos ret = new Pos()
                        .setOid(_dto.getOid())
                        .setName(_dto.getName())
                        .setCompany(new Company()
                                        .setName(_dto.getCompany().getName())
                                        .setTaxNumber(_dto.getCompany().getTaxNumber()));
        return ret;
    }

    public static CategoryDto toDto(final Category _entity)
    {
        return CategoryDto.builder()
                        .withOID(_entity.getOid())
                        .withName(_entity.getName())
                        .build();
    }

    public static TaxDto toDto(final Tax _entity)
    {
        return TaxDto.builder()
                        .withOID(_entity.getOid())
                        .withName(_entity.getName())
                        .withPercent(_entity.getPercent())
                        .build();
    }

    public static Tax toEntity(final TaxDto _dto)
    {
        final Tax ret = new Tax()
                        .setOid(_dto.getOid())
                        .setName(_dto.getName())
                        .setPercent(_dto.getPercent());
        return ret;
    }

    public static TaxEntry toEntity(final TaxEntryDto _dto)
    {
        final TaxEntry ret = new TaxEntry()
                        .setTax(Converter.toEntity(_dto.getTax()))
                        .setAmount(_dto.getAmount());
        return ret;
    }

    public static TaxEntryDto toDto(final TaxEntry _entity)
    {
        return TaxEntryDto.builder()
                        .withTax(Converter.toDto(_entity.getTax()))
                        .withAmount(_entity.getAmount())
                        .build();
    }
}
