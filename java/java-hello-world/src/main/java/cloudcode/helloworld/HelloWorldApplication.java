package cloudcode.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class HelloWorldApplication {
	public static void main(String[] args) throws Exception {
		String value = System.getenv("PORT");
		if (value == null){
			System.out.println("error: PORT environment variable not set");
			System.exit(1);
		}
		SpringApplication.run(HelloWorldApplication.class, args);
	}

}
