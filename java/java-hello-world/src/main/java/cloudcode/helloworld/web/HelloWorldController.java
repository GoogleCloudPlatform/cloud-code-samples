
package cloudcode.helloworld.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public final class HelloWorldController {

    /**
     * endpoint for the landing page
     * @return a simple hello world message
     */
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }
}
