package cloudcode.guestbook.backend;

public class UserResponse {

    public Boolean success;
    public String errorMessage;
  
    public UserResponse(boolean success, String errorMessage) {
      this.success = success;
      this.errorMessage = errorMessage;
    }
  }
  