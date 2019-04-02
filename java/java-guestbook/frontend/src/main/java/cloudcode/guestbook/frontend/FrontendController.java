package cloudcode.guestbook.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.*;
import java.io.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Controller
public class FrontendController {

    private String backendUri = String.format("http://%s/messages", System.getenv("GUESTBOOK_API_ADDR")); 

    @GetMapping("/")
    public String main(Model model) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        GuestBookEntry[] response = restTemplate.getForObject(backendUri, GuestBookEntry[].class);
        model.addAttribute("messages", response);
        return "home";
    }

    @RequestMapping(value="/post", method=RequestMethod.POST)
    public String post(GuestBookEntry formMessage) throws IOException, URISyntaxException {
        URI url = new URI(backendUri);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity <GuestBookEntry> httpEntity = new HttpEntity <GuestBookEntry> (formMessage, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, httpEntity, String.class);

        return "redirect:/";
    }

}
