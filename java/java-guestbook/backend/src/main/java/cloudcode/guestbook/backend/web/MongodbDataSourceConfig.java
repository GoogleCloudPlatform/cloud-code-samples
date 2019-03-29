package cloudcode.guestbook.backend.web;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.context.annotation.PropertySource;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


@Configuration
public class MongodbDataSourceConfig extends AbstractMongoConfiguration {

    @Override
    public String getDatabaseName(){
        return "admin";
    }

    /*
    @Override
    @Bean
    public Mongo mongo() throws Exception {

        ServerAddress serverAddress = new ServerAddress("mongo-service");
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createScramSha1Credential(
                "root",
                "admin",
                "example".toCharArray()
        ));
        MongoClientOptions options = new MongoClientOptions.Builder()
            .build();
        return new MongoClient(serverAddress, credentials, options);
    }
    */
    @Override
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress("mongo-service");
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createScramSha1Credential(
                "root",
                "admin",
                "example".toCharArray()
        ));
        return new MongoClient(serverAddress, credentials);
    }
}