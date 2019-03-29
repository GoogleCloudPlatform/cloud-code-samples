package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    @GetMapping("/messages")
    public List<Map<String, String>> getMessages() {
        return repository.findAll();
    }

    @PostMapping("/messages")
    public void post(@RequestBody Map<String, String> formMessage) {
        System.out.println("test");
    }

}