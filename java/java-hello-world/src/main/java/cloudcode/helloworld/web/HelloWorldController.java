
package cloudcode.helloworld.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public final class HelloWorldController {

    // returns a simple response
    @RequestMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello!";
    }
}
