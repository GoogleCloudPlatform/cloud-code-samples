package cloudcode.guestbook.frontend.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.Map;
import java.util.ArrayList;

import java.net.*;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Controller
public class FrontendController {

    private String backendUri = String.format("http://%s/messages", System.getenv("GUESTBOOK_API_ADDR")); 

    @GetMapping("/")
    public String main(Model model) throws IOException {
        URL url = new URL(backendUri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setReadTimeout(100);

        InputStreamReader reader = new InputStreamReader(con.getInputStream(), "UTF-8");
        ArrayList<Map<String, String>> messageList = new Gson().fromJson(reader, new TypeToken<ArrayList<Map<String, String>>>(){}.getType());
        model.addAttribute("messages", messageList);
        return "home";
    }

    @RequestMapping(value="/post", method=RequestMethod.POST)
    public String post(FormMessage formMessage) throws IOException, URISyntaxException {
        URI url = new URI(backendUri);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
    
        
        HttpEntity <String> httpEntity = new HttpEntity <String> (formMessage.toString(), httpHeaders);
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, httpEntity, String.class);

        return "redirect:/";
    }

}
