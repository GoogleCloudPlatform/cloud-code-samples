package cloudcode.guestbook.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth)
    throws Exception {
    UserDetailsService userDetailsService = mongoUserDetails();
    auth
      .userDetailsService(userDetailsService);
    //   .passwordEncoder(bCryptPasswordEncoder);
  }

//   @Autowired
//   private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean
  public UserDetailsService mongoUserDetails() {
    return new CustomUserDetailsService();
  }
}
