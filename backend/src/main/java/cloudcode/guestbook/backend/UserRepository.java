package cloudcode.guestbook.backend;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores User objects
 */
public interface UserRepository extends MongoRepository<User, String> {
  List<User> findByUsernameAndPassword(String username, String password);
  User findByEmail(String email);
  User findByUsername(String username);
}
