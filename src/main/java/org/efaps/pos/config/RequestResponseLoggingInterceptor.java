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

package org.efaps.pos.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RequestResponseLoggingInterceptor
    implements ClientHttpRequestInterceptor
{

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(final HttpRequest _request,
                                        final byte[] _body,
                                        final ClientHttpRequestExecution _execution)
        throws IOException
    {
        logRequest(_request, _body);
        final ClientHttpResponse response = _execution.execute(_request, _body);
        logResponse(response);
        return response;
    }

    private void logRequest(final HttpRequest _request,
                            final byte[] _body)
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("===========================request begin=============================================");
            LOG.debug("URI         : {}", _request.getURI());
            LOG.debug("Method      : {}", _request.getMethod());
            LOG.debug("Headers     : {}", _request.getHeaders());
            LOG.debug("Request body: {}", new String(_body, StandardCharsets.UTF_8));
            LOG.debug("==========================request end================================================");
        }
    }

    private void logResponse(final ClientHttpResponse _response)
        throws IOException
    {
        if (LOG.isDebugEnabled()) {
            LOG.debug("============================response begin===========================================");
            LOG.debug("Status code  : {}", _response.getStatusCode());
            LOG.debug("Status text  : {}", _response.getStatusText());
            LOG.debug("Headers      : {}", _response.getHeaders());
            LOG.debug("Response body: {}", StreamUtils.copyToString(_response.getBody(), StandardCharsets.UTF_8));
            LOG.debug("=======================response end===================================================");
        }
    }
}
