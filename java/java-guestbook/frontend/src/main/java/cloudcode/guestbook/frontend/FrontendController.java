/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cloudcode.guestbook.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class FrontendController {

    private String backendUri = String.format("http://%s/messages",
        System.getenv("GUESTBOOK_API_ADDR"));

    /**
     * endpoint for the landing page
     * @param model defines model for html template
     * @return the name of the html template to render
     */
    @GetMapping("/")
    public final String main(final Model model) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            GuestBookEntry[] response = restTemplate.getForObject(backendUri,
            GuestBookEntry[].class);
            model.addAttribute("messages", response);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving messages from backend.");
            model.addAttribute("noBackend", true);
        }

        return "home";
    }

    /**
     * endpoint for handling form submission
     * @param formMessage holds date entered in html form
     * @return redirects back to home page
     * @throws URISyntaxException when there is an issue with the backend uri
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public final String post(final GuestBookEntry formMessage)
            throws URISyntaxException {
        URI url = new URI(backendUri);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<GuestBookEntry> httpEntity =
            new HttpEntity<GuestBookEntry>(formMessage, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(url, httpEntity, String.class);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error posting message to backend.");
        }

        return "redirect:/";
    }

}
