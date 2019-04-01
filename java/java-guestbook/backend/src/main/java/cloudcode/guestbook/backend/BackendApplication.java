package cloudcode.guestbook.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        final String expectedVars[] = {"PORT", "GUESTBOOK_DB_ADDR"};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null){
                System.out.format("error: %s environment variable not set\n", v);
                System.exit(1);
            }
        }
        SpringApplication.run(BackendApplication.class, args);
    }

}