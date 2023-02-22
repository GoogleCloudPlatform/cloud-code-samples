package com.cloudcode.dataflow.example.options;

import java.io.Serializable;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubClient;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubClient.SubscriptionPath;

public class SubscriptionPathOptionValue implements Serializable {
  private final SubscriptionPath subscriptionPath;

  public SubscriptionPathOptionValue(String input) {
    SubscriptionPath parsedResult = null;
    try {
      parsedResult = PubsubClient.subscriptionPathFromPath(input);
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException(
          String.format(
              "error parsing '%s' into %s: %s", input, SubscriptionPath.class, e.getMessage()));
    }
    this.subscriptionPath = parsedResult;
  }

  public SubscriptionPath getValue() {
    return subscriptionPath;
  }
}
