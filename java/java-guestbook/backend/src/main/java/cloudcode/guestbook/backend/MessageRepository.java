package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

public interface MessageRepository extends MongoRepository<GuestBookEntry, String> {

}