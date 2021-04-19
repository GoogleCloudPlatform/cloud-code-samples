package cloudcode.guestbook.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * manages Mongo configuration data
 */
@Configuration
public class MongodbDataSourceConfig extends AbstractMongoConfiguration {

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
        ServerAddress serverAddress = new ServerAddress(
            System.getenv("GUESTBOOK_DB_ADDR"));
        return new MongoClient(serverAddress);
    }
}
