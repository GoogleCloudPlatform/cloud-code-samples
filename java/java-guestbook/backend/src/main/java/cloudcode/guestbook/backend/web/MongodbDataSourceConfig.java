package cloudcode.guestbook.backend.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


@Configuration
public class MongodbDataSourceConfig extends AbstractMongoConfiguration {

    @Override
    public String getDatabaseName(){
        return "admin";
    }

    @Override
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(System.getenv("MONGO_HOST"));
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createScramSha1Credential(
                System.getenv("MONGO_USERNAME"),
                "admin",
                System.getenv("MONGO_PASSWORD").toCharArray()
        ));
        return new MongoClient(serverAddress, credentials);
    }
}