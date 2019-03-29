package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    @GetMapping("/messages")
    public List<FormMessage> getMessages() {
        List<FormMessage> msgList = repository.findAll();
        return msgList;
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody FormMessage message) {
        repository.save(message);
    }
}