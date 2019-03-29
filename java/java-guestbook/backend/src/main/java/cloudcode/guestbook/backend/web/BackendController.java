package cloudcode.guestbook.backend.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class BackendController {

    @Autowired private MessageRepository repository;

    @GetMapping("/messages")
    public List<Map<String, String>> getMessages() {
        List<Map<String, String>> msgList = repository.findAll();
        return cleanList(msgList);
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody Map<String, String> message) {
        repository.save(cleanMap(message));
    }

    private  List<Map<String, String>> cleanList(List<Map<String, String>> inputList) {
        List<Map<String, String>> cleanedList = new ArrayList<Map<String, String>>();
        for (Map<String, String> msg : inputList) {
            cleanedList.add(cleanMap(msg));
        }
        return cleanedList;
    }

    private Map<String, String> cleanMap(Map<String, String> input){
        Map<String, String> cleaned = new HashMap<String, String>(input);
        ArrayList<String> keys = new ArrayList<>(input.keySet());
        for (String k : keys){
            if (! k.equals("Author") && ! k.equals("Message") && !k.equals("Date")){
                cleaned.remove(k);
            }
        }
        return cleaned;
    }
}