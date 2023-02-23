package com.cloudcode.dataflow;

import com.cloudcode.dataflow.example.io.ReadPubsubToRow;
import com.cloudcode.dataflow.example.io.ReadResult;
import com.cloudcode.dataflow.example.io.WriteResult;
import com.cloudcode.dataflow.example.io.WriteRowToBigquery;
import com.cloudcode.dataflow.example.logging.Log;
import com.cloudcode.dataflow.example.options.ReadPubsubWriteBigQueryOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.TypeDescriptor;

/**
 * This pipeline reads JSON encoded strings from Pub/Sub and Writes the processed structured data to
 * BigQuery.
 */
public class ReadPubsubWriteBigQuery {
  public static void main(String[] args) {
    ReadPubsubWriteBigQueryOptions options =
        PipelineOptionsFactory.fromArgs(args)
            .withValidation()
            .as(ReadPubsubWriteBigQueryOptions.class);

    Pipeline pipeline = Pipeline.create(options);

    ReadResult readResult =
        pipeline.apply(
            "Read subscription into Rows",
            ReadPubsubToRow.of(
                options.getSubscription().getValue(), TypeDescriptor.of(ExampleModel.class)));

    readResult
        .getError()
        .apply(
            "Log read errors", Log.error("[pubsub][json-to-row]", ReadPubsubWriteBigQuery.class));

    WriteResult writeResult =
        readResult
            .getOutput()
            .apply("Write To BigQuery", WriteRowToBigquery.of(options.getDataset().getValue()));

    writeResult
        .getError()
        .apply(
            "Log write errors", Log.error("[bigquery][row-to-bigquery]", WriteRowToBigquery.class));

    pipeline.run();
  }
}
