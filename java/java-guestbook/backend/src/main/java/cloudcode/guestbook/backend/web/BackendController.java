package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    @GetMapping("/messages")
    public List<Map<String, String>> getMessages() {
        List<Map<String, String>> msgList = repository.findAll();
        List<Map<String, String>> cleanedList = new ArrayList<Map<String, String>>();
        for (Map<String, String> msg : msgList) {
            ArrayList<String> keys = new ArrayList<>(msg.keySet());
            for (String k : keys){
                if (! k.equals("Author") && ! k.equals("Message") && !k.equals("Date")){
                    msg.remove(k);
                }
            }
            cleanedList.add(msg);
        }
        return cleanedList;
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody Map<String, String> message) {
        repository.save(message);
    }

}