/*
 * Copyright 2023 Google LLC
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

package cloudcode.guestbook.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * manages Mongo configuration data
 */
@Configuration
public class MongodbDataSourceConfig extends AbstractMongoClientConfiguration {

    /**
     * the name of the mongo database to use
     */
    @Override
    public final String getDatabaseName() {
        return "guestbook";
    }

    /**
     * manages connects to the mongo service address passed in as an
     * environment variable
     */
    @Override
    public final MongoClient mongoClient() {
        ConnectionString connectionString =
                new ConnectionString("mongodb://" + System.getenv("GUESTBOOK_DB_ADDR"));
        return MongoClients.create(connectionString);
    }
}
