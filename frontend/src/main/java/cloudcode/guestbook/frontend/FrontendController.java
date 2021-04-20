package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class FrontendController {

  private String backendUri = String.format(
    "http://%s/messages",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  private String signupUri = String.format(
    "http://%s/signup",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  private String loginUri = String.format(
    "http://%s/login",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  /**
   * endpoint for the landing page
   * @param model defines model for html template
   * @return the name of the html template to render
   */
  @GetMapping("/")
  public final String main(final Model model) {
    RestTemplate restTemplate = new RestTemplate();
    GuestBookEntry[] response = restTemplate.getForObject(
      backendUri,
      GuestBookEntry[].class
    );
    model.addAttribute("messages", response);
    return "home";
  }

  /**
   * endpoint for the landing page
   * @param model defines model for html template
   * @return the name of the html template to render
   */
  @GetMapping("/test")
  public final String test(
    final Model model,
    @RequestParam("username") String username,
    @RequestParam("password") String password
  )
    throws URISyntaxException {
    URI uri = new URI(
      String.format("%s?username=%s&password=%s", loginUri, username, password)
    );
    RestTemplate restTemplate = new RestTemplate();
    GuestBookEntry[] response = restTemplate.getForObject(
      uri,
      GuestBookEntry[].class
    );
    model.addAttribute("messages", response);
    return "test";
  }

  /**
   * endpoint for handling form submission
   * @param formMessage holds date entered in html form
   * @return redirects back to home page
   * @throws URISyntaxException when there is an issue with the backend uri
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public final String post(final GuestBookEntry formMessage)
    throws URISyntaxException {
    URI url = new URI(signupUri);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    HttpEntity<GuestBookEntry> httpEntity = new HttpEntity<GuestBookEntry>(
      formMessage,
      httpHeaders
    );
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForObject(url, httpEntity, String.class);

    return "redirect:/";
  }

  /**
   * endpoint for handling form submission
   * @param formMessage holds date entered in html form
   * @return redirects back to home page
   * @throws URISyntaxException when there is an issue with the backend uri
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public final String login(final GuestBookEntry formMessage)
    throws URISyntaxException {
    URI url = new URI(signupUri);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    HttpEntity<GuestBookEntry> httpEntity = new HttpEntity<GuestBookEntry>(
      formMessage,
      httpHeaders
    );
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForObject(url, httpEntity, String.class);

    return "redirect:/test/";
  }
}
