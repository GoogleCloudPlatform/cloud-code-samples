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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test for local or remote service based on the env var
 * "SERVICE_URL". See java/CONTRIBUTING.MD for more information. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HelloWorldControllerIT {

  @Test
  public void respondsToHttpRequest() throws IOException {
    String port = System.getenv("PORT");
    if (port == null || port == "") {
      port = "8080";
    }

    String url = System.getenv("SERVICE_URL");
    if (url == null || url == "") {
      url = "http://localhost:" + port;
    }

    String token = System.getenv("TOKEN");

    OkHttpClient ok =
        new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    Request request = new Request.Builder().url(url + "/").header("Authorization", "Bearer " + token).get().build();

    String expected = "Congratulations, you successfully deployed a container image to Cloud Run";
    Response response = ok.newCall(request).execute();
    assertThat(response.body().string(), containsString(expected));
    assertThat(response.code(), equalTo(200));
  }
}
