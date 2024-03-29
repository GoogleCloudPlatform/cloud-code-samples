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

/** {@link ReadResult} bundles a {@link Row} and String errors {@link PCollection}. */
public class ReadResult implements POutput {

  static ReadResult of(
      Pipeline pipeline,
      TupleTag<Row> outputTag,
      PCollection<Row> output,
      TupleTag<String> errorTag,
      PCollection<String> error) {
    return new ReadResult(pipeline, outputTag, output, errorTag, error);
  }

  private final Pipeline pipeline;
  private final TupleTag<Row> outputTag;
  private final PCollection<Row> output;

  private final TupleTag<String> errorTag;
  private final PCollection<String> error;

  private ReadResult(
      Pipeline pipeline,
      TupleTag<Row> outputTag,
      PCollection<Row> output,
      TupleTag<String> errorTag,
      PCollection<String> error) {
    this.pipeline = pipeline;
    this.outputTag = outputTag;
    this.output = output;
    this.errorTag = errorTag;
    this.error = error;
  }

  public PCollection<Row> getOutput() {
    return output;
  }

  public PCollection<String> getError() {
    return error;
  }

  @Override
  public Pipeline getPipeline() {
    return this.pipeline;
  }

  @Override
  public Map<TupleTag<?>, PValue> expand() {
    return ImmutableMap.of(
        this.outputTag, this.output,
        this.errorTag, this.error);
  }

  @Override
  public void finishSpecifyingOutput(
      String transformName, PInput input, PTransform<?, ?> transform) {}
}
