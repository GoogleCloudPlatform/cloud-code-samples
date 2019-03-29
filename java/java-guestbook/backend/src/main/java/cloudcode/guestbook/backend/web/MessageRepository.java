package cloudcode.guestbook.backend.web;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<FormMessage, String> {

}