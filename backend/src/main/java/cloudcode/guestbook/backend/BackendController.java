package cloudcode.guestbook.backend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
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

  @PostMapping("/login")
  public void login(@RequestBody User user){
      // TODO: fix
      System.out.print(user.getUsername());
      System.out.print(user.getPassword());
    User match = repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
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
    if (userService.findUserByEmail(user.getEmail()) != null) {
      return new UserResponse(
        false,
        "There is already a user registered with that email"
      );
    } else {
      repository.save(user);
      return new UserResponse(true, null);
    }
  }
}
