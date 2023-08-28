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

package cloudcode.guestbook.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * this class serves as an entry point for the Spring Boot app
 * Here, we check to ensure all required environment variables are set
 */
@SpringBootApplication
public class FrontendApplication {

    public static void main(final String[] args) {
        final String[] expectedVars = {"PORT", "GUESTBOOK_API_ADDR"};
        for (String v : expectedVars) {
            String value = System.getenv(v);
            if (value == null) {
                System.out.format("error: %s environment variable not set", v);
                System.exit(1);
            }
        }
        SpringApplication.run(FrontendApplication.class, args);
    }

}
