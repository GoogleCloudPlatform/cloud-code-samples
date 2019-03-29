package cloudcode.guestbook.backend.web;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

public interface MessageRepository extends MongoRepository<Map<String, String>, String> {

}