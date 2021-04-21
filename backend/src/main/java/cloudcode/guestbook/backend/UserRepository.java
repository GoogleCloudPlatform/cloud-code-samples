package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores User objects
 */
public interface UserRepository extends MongoRepository<User, String> {
  User findByUsernameAndPassword(String username, String password);
  User findByEmail(String email);
  User findByUsername(String username);
}
