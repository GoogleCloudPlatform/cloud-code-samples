package cloudcode.helloworld.web;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Defines a controller to handle HTTP requests */
@Controller
public final class HelloWorldController {

  private static String project;
  private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

  /**
   * Create an endpoint for the landing page
   *
   * @return the index view template
   */
  @GetMapping("/")
  public String helloWorld(Model model) {
    // If the custom environment variable GOOGLE_CLOUD_PROJECT is not set
    // check the Cloud Run metadata server for the Project Id.
    project = System.getenv("GOOGLE_CLOUD_PROJECT");
    if (project == null) {
      project = getProjectId();
    }

    // Get Cloud Run environment variables.
    String revision = System.getenv("K_REVISION") == null ? "???" : System.getenv("K_REVISION");
    String service = System.getenv("K_SERVICE") == null ? "???" : System.getenv("K_SERVICE");

    // Set variables in html template.
    model.addAttribute("revision", revision);
    model.addAttribute("service", service);
    model.addAttribute("project", project);
    return "index";
  }

  /**
   * Get the Project Id from GCP metadata server
   *
   * @return GCP Project Id or null
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
      logger.error("Error retrieving Project Id");
    }
    return project;
  }
}
