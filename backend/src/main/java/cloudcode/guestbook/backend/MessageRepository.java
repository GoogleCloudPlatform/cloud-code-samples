package cloudcode.guestbook.backend;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores GuestBookEntry objects
 */
public interface MessageRepository
  extends MongoRepository<GuestBookEntry, String> {
  List<GuestBookEntry> findByUsernameAndPassword(
    String username,
    String password
  );
}
