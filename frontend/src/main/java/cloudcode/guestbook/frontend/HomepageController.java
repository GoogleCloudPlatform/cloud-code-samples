package cloudcode.guestbook.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class HomepageController {

  /**
   * endpoint for the landing page
   * @param model defines model for html template
   * @return the name of the html template to render
   */
  @GetMapping("/")
  public final String main(final Model model) {
    RestTemplate restTemplate = new RestTemplate();
    User[] response = restTemplate.getForObject(BackendURI.MESSAGES, User[].class);
    model.addAttribute("messages", response);
    return "home";
  }
}
