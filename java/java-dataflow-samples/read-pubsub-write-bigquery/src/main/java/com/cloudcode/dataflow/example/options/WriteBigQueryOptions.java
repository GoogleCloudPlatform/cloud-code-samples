package com.cloudcode.dataflow.example.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation.Required;

public interface WriteBigQueryOptions extends PipelineOptions {
  @Description("BigQuery Dataset")
  @Required
  @JsonIgnore
  DatasetReferenceOptionValue getDataset();

  void setDataset(DatasetReferenceOptionValue value);
}
