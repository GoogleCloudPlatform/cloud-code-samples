package cloudcode.guestbook.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    /**
     * endpoint for retrieving all guest book entries stored in database
     * @return a list of GuestBookEntry objects
     */
    @GetMapping("/messages")
    public final List<GuestBookEntry> getMessages() {
        Sort byCreation = new Sort(Sort.Direction.DESC, "_id");
        List<GuestBookEntry> msgList = repository.findAll(byCreation);
        return msgList;
    }

    /**
     * endpoint for adding a new guest book entry to the database
     * @param message a message object passed in the HTTP POST request
     */
    @PostMapping("/messages")
    public final void addMessage(@RequestBody GuestBookEntry message) {
        message.setDate(System.currentTimeMillis());
        repository.save(message);
    }
}
