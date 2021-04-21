package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private String loginUri = String.format(
    "http://%s/login",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  private static List<User> users = new ArrayList<User>();

  public CustomAuthenticationProvider() {
    // Example User
    users.add(new User("fake@email.com", "user1", "user1Pass"));
  }

  @Override
  public Authentication authenticate(Authentication authentication)
    throws AuthenticationException {
    String username = authentication.getPrincipal().toString();
    String password = authentication.getCredentials().toString();
    try {
      new RestTemplate()
      .postForObject(
          new URI(loginUri),
          new HttpEntity<User>(new User("", username, password)),
          SignupResponse.class
        );
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //TODO: call backend
    return new UsernamePasswordAuthenticationToken(username, password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
