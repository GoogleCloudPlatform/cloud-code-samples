package cloudcode.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * this class serves as an entry point for the Spring Boot app Here, we check to ensure all required
 * environment variables are set
 */
@SpringBootApplication
public class HelloWorldApplication {

  public static void main(final String[] args) throws Exception {
    String port = System.getenv("PORT");
    if (port == null) {
      System.out.println("error: PORT environment variable not set");
      System.exit(1);
    }

    SpringApplication.run(HelloWorldApplication.class, args);
    System.out.println(
        "Hello from Cloud Run! The container started successfully and is listening for HTTP requests on $PORT");
  }
}
