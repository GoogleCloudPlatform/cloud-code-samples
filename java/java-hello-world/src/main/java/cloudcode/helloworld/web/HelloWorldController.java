/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
     * Create an endpoint for the landing page
     * @return the index view template with a simple message
     */
    @GetMapping("/")
    public String helloWorld(Model model) {
        String message = "It's running!";
        model.addAttribute("message", message);
        return "index";
    }
}
