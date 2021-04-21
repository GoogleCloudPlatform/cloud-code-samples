package cloudcode.guestbook.backend;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  //   @Autowired
  //   private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        // pass empty list – no authorities
        new ArrayList<GrantedAuthority>()
      );
    } else {
      throw new UsernameNotFoundException("Username not found");
    }
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
