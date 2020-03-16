package cloudcode.helloworld.web;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HelloWorldControllerTests {

  @Autowired private MockMvc mvc;

  @Test
  public void getIndexView() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("service"))
        .andExpect(model().attributeExists("revision"));
  }

  @Test
  public void respondsToHttpRequest() throws IOException {
    String port = System.getenv("PORT");
    if (port == null) {
      port = "8080";
    }

    String url = System.getenv("SERVICE_URL");
    if (url == null) {
      url = "http://localhost:" + port;
    }

    OkHttpClient ok =
        new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build();

    Request request = new Request.Builder().url(url + "/").get().build();

    String expected = "Congratulations, you successfully deployed a container image to Cloud Run";
    Response response = ok.newCall(request).execute();
    assertThat(response.body().string(), containsString(expected));
    assertThat(response.code(), equalTo(200));
  }
}
