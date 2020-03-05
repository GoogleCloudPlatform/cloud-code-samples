package cloudcode.helloworld.web;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** defines the endpoints managed by the server. */
@Controller
public final class HelloWorldController {

  /**
   * endpoint for the landing page
   *
   * @return the index view
   */
  @GetMapping("/")
  public String helloWorld(Model model) {
    String revision = System.getenv("K_REVISION") == null ? "???" : System.getenv("K_REVISION");
    String service = System.getenv("K_SERVICE") == null ? "???" : System.getenv("K_SERVICE");
    String project = System.getenv("GOOGLE_CLOUD_PROJECT");
    if (project == null) {
      project = getProjectId();
    }
    System.out.println(service);
    model.addAttribute("revision", revision);
    model.addAttribute("service", service);
    model.addAttribute("project", project);
    return "index";
  }

  /**
   * Get the project ID from GCP metadata server
   *
   * @return your project Id or null
   */
  public static String getProjectId() {
    OkHttpClient ok =
        new OkHttpClient.Builder()
            .readTimeout(500, TimeUnit.MILLISECONDS)
            .writeTimeout(500, TimeUnit.MILLISECONDS)
            .build();

    String metadataUrl = "http://metadata.google.internal/computeMetadata/v1/project/project-id";
    Request request =
        new Request.Builder().url(metadataUrl).addHeader("Metadata-Flavor", "Google").get().build();

    String project = null;
    try {
      Response response = ok.newCall(request).execute();
      project = response.body().string();
    } catch (IOException e) {
      System.out.println("Error retrieving Project Id");
    }
    return project;
  }
}
