package cloudcode.guestbook.frontend;

/**
 * defines the data associated with a single guest book entry
 */
public class GuestBookEntry {

    private String author;
    private String message;
    private long date;

    public final String getAuthor() {
        return author;
    }

    public final void setAuthor(String author) {
        this.author = author;
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
