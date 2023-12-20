/*
 * Copyright 2003 - 2023 The eFaps Team
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

import java.lang.reflect.InvocationTargetException;

import org.efaps.pos.ConfigProperties;
import org.efaps.pos.ConfigProperties.Company;
import org.efaps.pos.client.EFapsClient;
import org.efaps.pos.context.Context;
import org.efaps.pos.dto.ReportToBaseDto;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService
{

    private final ConfigProperties configProperties;
    private final EFapsClient eFapsClient;

    public MonitoringService(final ConfigProperties configProperties,
                             final EFapsClient eFapsClient)
    {
        this.configProperties = configProperties;
        this.eFapsClient = eFapsClient;
    }

    public void reportToBase()
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        final var dto = ReportToBaseDto.builder()
                        .withVersion(configProperties.getVersion())
                        .build();
        if (configProperties.getCompanies().size() > 0) {
            for (final Company company : configProperties.getCompanies()) {
                Context.get().setCompany(company);
                MDC.put("company", company.getTenant());
                eFapsClient.postReportToBase(dto);
            }
        } else {
            eFapsClient.postReportToBase(dto);
        }
    }
}
