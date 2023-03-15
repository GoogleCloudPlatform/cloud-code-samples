package com.cloudcode.dataflow.example.io;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PInput;
import org.apache.beam.sdk.values.POutput;
import org.apache.beam.sdk.values.PValue;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;

/** {@link WriteResult} bundles a {@link Row} and String errors {@link PCollection}. */
public class WriteResult implements POutput {

  static WriteResult of(
      Pipeline pipeline,
      TupleTag<Row> successTag,
      PCollection<Row> success,
      TupleTag<String> errorTag,
      PCollection<String> error) {
    return new WriteResult(pipeline, successTag, success, errorTag, error);
  }

  private final Pipeline pipeline;

  private final TupleTag<Row> successTag;
  private final PCollection<Row> success;

  private final TupleTag<String> errorTag;

  private final PCollection<String> error;

  private WriteResult(
      Pipeline pipeline,
      TupleTag<Row> successTag,
      PCollection<Row> success,
      TupleTag<String> errorTag,
      PCollection<String> error) {
    this.pipeline = pipeline;
    this.successTag = successTag;
    this.success = success;
    this.errorTag = errorTag;
    this.error = error;
  }

  @Override
  public Pipeline getPipeline() {
    return pipeline;
  }

  public PCollection<Row> getSuccess() {
    return success;
  }

  public PCollection<String> getError() {
    return error;
  }

  @Override
  public Map<TupleTag<?>, PValue> expand() {
    return ImmutableMap.of(
        successTag, success,
        errorTag, error);
  }

  @Override
  public void finishSpecifyingOutput(
      String transformName, PInput input, PTransform<?, ?> transform) {}
}
