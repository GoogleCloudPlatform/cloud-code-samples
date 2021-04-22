package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
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

  @Override
  public Authentication authenticate(Authentication authentication)
    throws AuthenticationException {
    String username = authentication.getPrincipal().toString();
    String password = authentication.getCredentials().toString();
    URI url;

    try {
      url = new URI(loginUri);
    } catch (URISyntaxException e) {
      throw new AuthenticationServiceException(
        "Could not construct backend URL!"
      );
    }

    UserResponse result = new RestTemplate()
    .postForObject(
        url,
        new HttpEntity<User>(new User("", username, password)),
        UserResponse.class
      );
    if (result.success) {
      return new UsernamePasswordAuthenticationToken(username, password);
    } else {
      throw new BadCredentialsException(result.errorMessage);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
