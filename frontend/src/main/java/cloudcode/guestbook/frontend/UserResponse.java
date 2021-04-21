package cloudcode.guestbook.frontend;

import org.springframework.security.core.AuthenticationException;

public class UserResponse {

  public Boolean success;
  public AuthenticationException exception;

  public UserResponse(boolean success, AuthenticationException exception) {
    this.success = success;
    this.exception = exception;
  }
}
