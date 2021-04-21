package cloudcode.guestbook.backend;

import javax.naming.AuthenticationException;

public class UserResponse {

  public Boolean success;
  public AuthenticationException exception;

  public UserResponse(boolean success, AuthenticationException exception) {
    this.success = success;
    this.exception = exception;
  }
}
