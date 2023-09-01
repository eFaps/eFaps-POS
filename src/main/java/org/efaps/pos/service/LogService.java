/*
 * Copyright 2003 - 2021 The eFaps Team
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

import org.efaps.pos.ConfigProperties.LogToken;
import org.efaps.pos.dto.LogDto;
import org.efaps.pos.entity.LogEntry;
import org.efaps.pos.repository.LogEntryRepository;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class LogService
{

    private final LogEntryRepository logEntryRepository;

    public LogService(final LogEntryRepository logEntryRepository)
    {
        this.logEntryRepository = logEntryRepository;
    }

    public void register(final LogToken logToken,
                         final LogDto logDto)
    {
        logEntryRepository.save(new LogEntry()
                        .setIdent(logToken.getIdent())
                        .setKey(logDto.getKey())
                        .setValue(logDto.getValue())
                        .setLevel(logDto.getLevel()));
    }

    public void error(final String ident,
                      final String key,
                      final String value)
    {
        logEntryRepository.save(new LogEntry()
                        .setIdent(ident)
                        .setKey(key)
                        .setValue(value)
                        .setLevel(LogLevel.ERROR));
    }
}
