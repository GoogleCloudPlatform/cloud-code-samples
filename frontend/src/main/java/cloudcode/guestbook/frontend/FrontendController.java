package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
   * endpoint for the login page
   * @return the name of the html template to render
   */
  @GetMapping("/login")
  public final String login() {
    return "login";
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
    User[] response = restTemplate.getForObject(uri, User[].class);
    model.addAttribute("messages", response);
    return "test";
  }

  /**
   * endpoint for handling form submission
   * @param User holds date entered in html form
   * @return redirects back to home page
   * @throws URISyntaxException when there is an issue with the backend uri
   */
  @PostMapping("/signup")
  public RedirectView post(final User user, RedirectAttributes attributes)
    throws URISyntaxException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    UserResponse response = new RestTemplate()
    .postForObject(
        new URI(signupUri),
        new HttpEntity<User>(user, httpHeaders),
        UserResponse.class
      );

    RedirectView view = new RedirectView("/login");
    if (response.success) {
      attributes.addFlashAttribute("username", user.getUsername());
      attributes.addFlashAttribute("password", user.getPassword());
      attributes.addFlashAttribute("autologin", "autologin");
    } else {
      attributes.addFlashAttribute(
        "errorMessage",
        "Error: " + response.errorMessage
      );
    }
    return view;
  }

  @GetMapping("/login-error")
  public String login(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
    String errorMessage = null;
    if (session != null) {
      AuthenticationException ex = (AuthenticationException) session.getAttribute(
        WebAttributes.AUTHENTICATION_EXCEPTION
      );
      if (ex != null) {
        errorMessage = ex.getMessage();
      }
    }
    model.addAttribute("errorMessage", errorMessage);
    return "login";
  }

  @PostMapping("/tokensignin")
  public final String tokensignin(@RequestBody final User user) {
    // DEBUG
    System.out.println(user == null);
    System.out.println(user);
    System.out.println(user.getPassword());
    System.out.println(user.getEmail());
    System.out.println(user.getUsername());
    return "home";
  }

  @Autowired
  protected AuthenticationManager authenticationManager;
}
