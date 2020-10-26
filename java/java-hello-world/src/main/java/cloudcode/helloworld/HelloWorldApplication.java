package cloudcode.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class serves as an entry point for the Spring Boot app
 * Here, we check to ensure all required environment variables are set
 */
@SpringBootApplication
public class HelloWorldApplication {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(final String[] args) throws Exception {
        String port = System.getenv("PORT");
        if (port == null) {
            logger.warn("$PORT environment variable not set");
        }
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
