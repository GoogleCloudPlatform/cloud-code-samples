package cloudcode.guestbook.backend.web;

public class FormMessage {

    private String author = "";
    private String message = "";
    private String date = "";

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate(){
        return this.date;
    }

}