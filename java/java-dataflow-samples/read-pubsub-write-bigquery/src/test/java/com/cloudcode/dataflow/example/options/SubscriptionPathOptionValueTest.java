package com.cloudcode.dataflow.example.options;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubClient;
import org.junit.jupiter.api.Test;

class SubscriptionPathOptionValueTest {

  @Test
  void validValueBuildsSubscriptionPath() {
    String option = "projects/aProject/subscriptions/aSubscription";
    SubscriptionPathOptionValue optionValue = new SubscriptionPathOptionValue(option);
    PubsubClient.SubscriptionPath actual = optionValue.getValue();
    assertEquals("aSubscription", actual.getName());
    assertEquals(option, actual.getPath());
    assertEquals("/subscriptions/aProject/aSubscription", actual.getFullPath());
  }

  @Test
  void inputErrorInformsValidFormat() {
    String option = "aSubscription";
    IllegalArgumentException error =
        assertThrows(IllegalArgumentException.class, () -> new SubscriptionPathOptionValue(option));
    String className = PubsubClient.SubscriptionPath.class.getName();
    String expected =
        String.format(
            "error parsing '%s' into class %s: "
                + "Malformed subscription path aSubscription: must be of the form \"projects/\" + <project id> + \"subscriptions\"",
            option, className);
    assertEquals(expected, error.getMessage());
  }
}
