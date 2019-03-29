package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;
    static final List<String> validKeys = Arrays.asList("Author", "Message", "Date");

    @GetMapping("/messages")
    public List<Map<String, String>> getMessages() {
        List<Map<String, String>> msgList = repository.findAll();
        List<Map<String, String>> cleanedList = new ArrayList<Map<String, String>>();
        for (Map<String, String> msg : msgList) {
            cleanedList.add(cleanMessage(msg));
        }
        return cleanedList;
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody Map<String, String> message) {
        repository.save(cleanMessage(message));
    }

    private Map<String, String> cleanMessage(Map<String, String> input){
        Map<String, String> cleaned = new HashMap<String, String>(input);
        ArrayList<String> keys = new ArrayList<>(input.keySet());
        for (String k : keys){
            if (! validKeys.contains(k)){
                cleaned.remove(k);
            }
        }
        return cleaned;
    }
}