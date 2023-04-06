package com.cloudcode.dataflow.example.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation.Required;

public interface ReadPubsubOptions extends PipelineOptions {
  @Description("Pub/Sub subscription")
  @Required
  @JsonIgnore
  SubscriptionPathOptionValue getSubscription();

  void setSubscription(SubscriptionPathOptionValue value);
}
