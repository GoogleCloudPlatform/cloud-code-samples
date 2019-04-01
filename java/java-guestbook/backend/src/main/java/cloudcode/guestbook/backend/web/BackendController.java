package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    @GetMapping("/messages")
    public List<GuestBookEntry> getMessages() {
        List<GuestBookEntry> msgList = repository.findAll(new Sort(Sort.Direction.DESC, "_id"));
        return msgList;
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody GuestBookEntry message) {
        message.setDate(System.currentTimeMillis());
        repository.save(message);
    }
}