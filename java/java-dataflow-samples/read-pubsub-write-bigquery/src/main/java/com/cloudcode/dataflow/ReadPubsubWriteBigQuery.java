/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  // A TypeDescriptor describes our ExampleModel class, namely its properties and other Java class
  // features. It is needed to tell Beam how to construct what is called a Beam Schema.
  // A Beam Schema is an important concept used throughout this example and will be revisited.
  // A Schema describes the properties of data elements and their data type.
  // See: https://beam.apache.org/documentation/programming-guide/#schemas
  //
  // This is where you would modify this sample to process JSON encoded Pub/Sub subscription
  // messages
  // for your own data. Just change ExampleModel to your own custom type.
  // See the ExampleModel class for more details on how to do this with respect to Beam Schemas.
  private static final TypeDescriptor<ExampleModel> TYPE_DESCRIPTOR =
      TypeDescriptor.of(ExampleModel.class);

  public static void main(String[] args) {

    // ReadPubsubWriteBigQueryOptions extends the PipelineOptions interface allowing the pipeline
    // by convention to instantiate from command-line arguments and validate its required values.
    // See https://beam.apache.org/documentation/programming-guide/#configuring-pipeline-options
    ReadPubsubWriteBigQueryOptions options =
        PipelineOptionsFactory.fromArgs(args)
            // Tells Beam to validate required properties of our custom options.
            .withValidation()
            // We use the 'as' method to switch between different PipelineOptions views; Beam stores
            // the state of these views between switches like this.
            .as(ReadPubsubWriteBigQueryOptions.class);

    // We instantiate a Pipeline from the ReadPubsubWriteBigQueryOptions.
    // See https://beam.apache.org/documentation/programming-guide/#creating-a-pipeline
    Pipeline pipeline = Pipeline.create(options);

    // The first step of the pipeline reads Pub/Sub JSON strings. Most introductions to Beam show
    // a PCollection as the return of an apply method.  Instead, we see here a ReadResult where
    // we are bundling two PCollections, an expected output PCollection and an error PCollection.
    // This multiple output pattern is best practice for situations where we might expect an error.
    // See https://beam.apache.org/documentation/programming-guide/#additional-outputs
    ReadResult readResult =
        pipeline.apply(
            "Read subscription into Rows",
            ReadPubsubToRow.of(options.getSubscription().getValue(), TYPE_DESCRIPTOR));

    // ReadResult's getError returns the error PCollection. Typically, we would send error
    // elements to what is called a dead letter queue. In this sample we simply write to log.
    readResult
        .getError()
        .apply(
            "Log read errors", Log.error("[pubsub][json-to-row]", ReadPubsubWriteBigQuery.class));

    // A WriteResult holds two PCollections - successful writes to BigQuery and any insertion
    // errors.
    // Notice we apply ReadResult's getOutput() to the PTransform that is responsible for writing
    // to BigQuery.
    WriteResult writeResult =
        readResult
            .getOutput()
            .apply("Write To BigQuery", WriteRowToBigquery.of(options.getDataset().getValue()));

    // Again like ReadResult.getError(), we log any errors from writing to BigQuery.
    writeResult
        .getError()
        .apply(
            "Log write errors", Log.error("[bigquery][row-to-bigquery]", WriteRowToBigquery.class));

    // After constructing the pipeline, we execute the run method.
    // Beam has two phases. A pipeline construction phase tells Beam all the steps we want to
    // perform
    // in processing our data.  A pipeline execution phase runs our code.  It's important to
    // consider these distinctions to understand what is in memory during either construction
    // phase and execution phase. This is a common source of headache when troubleshooting errors.
    pipeline.run();
  }
}
