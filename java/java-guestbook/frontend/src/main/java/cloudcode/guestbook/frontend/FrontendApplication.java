package cloudcode.guestbook.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontendApplication {

    public static void main(String[] args) {
        for (String expectedVar : new String[]{"PORT", "GUESTBOOK_API_ADDR"}) {
            String value = System.getenv(expectedVar);
            if (value == null){
                System.out.format("error: %s environment variable not set\n", expectedVar);
                System.exit(1);
            }
        }
        SpringApplication.run(FrontendApplication.class, args);
    }

}