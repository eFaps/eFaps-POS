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

package org.efaps.pos.context;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.efaps.pos.ConfigProperties;
import org.efaps.pos.ConfigProperties.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ContextFilter
    extends OncePerRequestFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(ContextFilter.class);

    private final ConfigProperties configProperties;

    public ContextFilter(final ConfigProperties _configProperties) {
        configProperties = _configProperties;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest _request,
                                    final HttpServletResponse _response,
                                    final FilterChain _filterChain)
        throws ServletException, IOException
    {
       final String companyKey = _request.getHeader("X-CONTEXT-COMPANY");
       if (StringUtils.isNotEmpty(companyKey)) {
           final Optional<Company> compOpt = configProperties.getCompanies().stream()
               .filter(company -> companyKey.equals(company.getKey()))
               .findFirst();
           if (compOpt.isPresent()) {
               Context.get().setCompany(compOpt.get());
           } else {
               LOG.debug("No company found for given X-CONTEXT-COMPANY Header", companyKey);
           }
       }
       _filterChain.doFilter(_request, _response);
    }
}
