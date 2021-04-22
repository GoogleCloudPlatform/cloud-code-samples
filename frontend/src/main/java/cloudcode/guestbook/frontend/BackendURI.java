package cloudcode.guestbook.frontend;

public class BackendURI {
    public static String MESSAGES = String.format(
        "http://%s/messages",
        System.getenv("GUESTBOOK_API_ADDR")
      );
}
