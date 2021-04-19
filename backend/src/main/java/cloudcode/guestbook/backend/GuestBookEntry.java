package cloudcode.guestbook.backend;

/**
 * defines the data associated with a single guest book entry
 */
public class GuestBookEntry {

    private String email;
    private String message;
    private long date;

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final long getDate() {
        return this.date;
    }

    public final void setDate(long date) {
        this.date = date;
    }
}
