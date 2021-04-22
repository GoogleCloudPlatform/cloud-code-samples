package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    User[] response = restTemplate.getForObject(backendUri, User[].class);
    model.addAttribute("messages", response);
    return "home";
  }

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
  public final String post(
    HttpServletRequest request,
    final Model model,
    final User user
  )
    throws URISyntaxException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    UserResponse response = new RestTemplate()
    .postForObject(
        new URI(signupUri),
        new HttpEntity<User>(user, httpHeaders),
        UserResponse.class
      );
    if (response.success) {
      //TODO: sign in user here...

      try {
        request.login(user.getUsername(), user.getPassword());
      } catch (ServletException e) {
        System.err.println("Error while logging in!");
        e.printStackTrace();
        model.addAttribute("errorMessage", "Error: Please Sign In Manually");
        return "login";
      }

      // DEBUG
      boolean authed = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .isAuthenticated();
      System.err.println("Authenticated? " + authed);

      return "redirect:/";
    } else {
      model.addAttribute("errorMessage", "Error: " + response.errorMessage);
      return "login";
    }
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
