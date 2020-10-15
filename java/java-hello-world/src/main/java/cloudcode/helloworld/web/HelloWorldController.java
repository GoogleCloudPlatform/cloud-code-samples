
package cloudcode.helloworld.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Defines a controller to handle HTTP requests.
 */
@Controller
public final class HelloWorldController {

    /**
     * endpoint for the landing page
     * @return a simple hello world message
     */
    @GetMapping("/")
    public String helloWorld(Model model) {
        String message = "It's runnnnnning!";
        model.addAttribute("message", message);
        return "index";
    }
}
