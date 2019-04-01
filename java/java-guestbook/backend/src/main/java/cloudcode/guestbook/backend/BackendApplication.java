package cloudcode.guestbook.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * this class serves as an entry point for the Spring Boot app
 * Here, we check to ensure all required environment variables are set
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        final String[] expectedVars = {"PORT", "GUESTBOOK_DB_ADDR"};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                System.out.format("error: %s environment variable not set", v);
                System.exit(1);
            }
        }
        SpringApplication.run(BackendApplication.class, args);
    }
}
