package cloudcode.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** This class serves as an entry point for the Spring Boot app. */
@SpringBootApplication
public class HelloWorldApplication {

  private static final Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);

  public static void main(final String[] args) throws Exception {
    String port = System.getenv("PORT");
    if (port == null) {
      logger.error("Error: $PORT environment variable not set");
      System.exit(1);
    }

    // Start the Spring Boot application.
    SpringApplication.run(HelloWorldApplication.class, args);
    logger.info(
        "Hello from Cloud Run! The container started successfully and is listening for HTTP requests on $PORT");
  }
}
