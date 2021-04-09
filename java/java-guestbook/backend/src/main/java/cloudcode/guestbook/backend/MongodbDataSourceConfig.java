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
