package cloudcode.guestbook.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        for (String expectedVar : new String[]{"PORT", "GUESTBOOK_DB_ADDR"}) {
            String value = System.getenv(expectedVar);
            if (value == null){
                System.out.format("error: %s environment variable not set\n", expectedVar);
                System.exit(1);
            }
        }
        SpringApplication.run(BackendApplication.class, args);
    }

}