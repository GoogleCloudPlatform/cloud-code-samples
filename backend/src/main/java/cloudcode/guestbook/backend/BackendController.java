package cloudcode.guestbook.backend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public class BackendController {

  @Autowired
  private UserRepository repository;

  /**
   * endpoint for retrieving all guest book entries stored in database
   * @return a list of User objects
   */
  @GetMapping("/messages")
  public final List<User> getMessages() {
    Sort byCreation = new Sort(Sort.Direction.DESC, "_id");
    List<User> msgList = repository.findAll(byCreation);
    return msgList;
  }

  /**
   * endpoint for retrieving all guest book entries stored in database
   * @return a list of User objects
   */
  @GetMapping("/login")
  public final List<User> login(
    @RequestParam("username") String username,
    @RequestParam("password") String password
  ) {
    List<User> msgList = repository.findByUsernameAndPassword(
      username,
      password
    );
    return msgList;
  }

  /**
   * endpoint for adding a new guest book entry to the database
   * @param message a message object passed in the HTTP POST request
   */
  @PostMapping("/messages")
  public final void addMessage(@RequestBody User message) {
    message.setDate(System.currentTimeMillis());
    repository.save(message);
  }

  @PostMapping("/signup")
  public final void addUser(@RequestBody User message) {
    message.setDate(System.currentTimeMillis());
    repository.save(message);
  }
}
