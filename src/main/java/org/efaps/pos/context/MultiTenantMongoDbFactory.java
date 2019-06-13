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

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MultiTenantMongoDbFactory extends SimpleMongoDbFactory {

  public MultiTenantMongoDbFactory(final MongoClientURI _uri) {
    super(_uri);
  }

  @Override
  public MongoDatabase getDb() throws DataAccessException {
    if (Context.get().getCompany() != null) {
      return getDb(Context.get().getCompany().getTenant());
    }
    return super.getDb("eFaps");
  }
}