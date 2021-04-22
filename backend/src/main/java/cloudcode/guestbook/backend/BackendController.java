package cloudcode.guestbook.backend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/login")
  public final UserResponse login(@RequestBody User user) {
    User userByName = repository.findByUsername(user.getUsername());
    User match = repository.findByUsernameAndPassword(
      user.getUsername(),
      user.getPassword()
    );

    // DEBUG
    System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword());

    if (userByName == null) {
      return new UserResponse(false, "No Account with Username");
    } else if (match == null) {
      return new UserResponse(false, "Incorrect Password");
    } else {
      return new UserResponse(true, null);
    }
  }

  /**
   * endpoint for adding a new guest book entry to the database
   * @param message a message object passed in the HTTP POST request
   */
  @PostMapping("/messages")
  public final void addMessage(@RequestBody User user) {
    repository.save(user);
  }

  @Autowired
  private CustomUserDetailsService userService;

  @PostMapping("/signup")
  public final UserResponse addUser(@RequestBody User user) {
    Boolean emailExists = userService.findUserByEmail(user.getEmail()) != null;
    Boolean usernameExists =
      userService.findUserByUsername(user.getUsername()) != null;
    if (emailExists) {
      return new UserResponse(false, "Email already registered");
    } else if (usernameExists) {
      return new UserResponse(false, "Username already registered");
    } else {
      repository.save(user);
      return new UserResponse(true, null);
    }
  }
}
