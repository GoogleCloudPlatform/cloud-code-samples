package cloudcode.guestbook.backend;

public class SignupResponse {
    public Boolean success;
    public String errorMessage;

    public SignupResponse(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }
}
