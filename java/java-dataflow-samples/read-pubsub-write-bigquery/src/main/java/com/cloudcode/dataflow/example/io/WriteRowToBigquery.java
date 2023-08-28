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

package com.cloudcode.dataflow.example.io;

import static java.util.Objects.requireNonNull;
import static org.apache.beam.sdk.values.TypeDescriptors.rows;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

import com.google.api.services.bigquery.model.DatasetReference;
import com.google.api.services.bigquery.model.TableReference;
import java.util.UUID;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.WriteDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryUtils;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;

/**
 * {@link WriteRowToBigquery} writes a {@link PCollection} and returns a {@link WriteResult}.
 *
 * <p>Feel free to reuse this class for your own custom types without any changes, with the
 * following considerations: 1) your class follows the pattern found in {@link
 * com.cloudcode.dataflow.ExampleModel}, 2) your class has properties that map to BigQuery table
 * schema types; see {@link BigQueryIO} for more details.
 */
public class WriteRowToBigquery extends PTransform<PCollection<Row>, WriteResult> {
  private static final TupleTag<Row> SUCCESS = new TupleTag<>() {};
  private static final TupleTag<String> ERROR = new TupleTag<>() {};

  public static WriteRowToBigquery of(DatasetReference datasetReference) {
    return new WriteRowToBigquery(datasetReference);
  }

  private final DatasetReference datasetReference;

  private WriteRowToBigquery(DatasetReference datasetReference) {
    this.datasetReference = datasetReference;
  }

  @Override
  public WriteResult expand(PCollection<Row> input) {
    // We implement this transform using key steps, namely write to BigQuery and collect any errors.

    // Notice that the PCollection Row input contains the Beam Schema.  This is a powerful reason
    // why we convert to Beam Rows, allowing us to read from and write to numerous resources
    // using a single data structure.
    Schema schema = input.getSchema();

    // Notice that we acquire the project and dataset from the datasetReference, derived from
    // our PipelineOptions.  However, we autogenerate the table name so that we create a unique
    // BigQuery table in a later step.
    TableReference tableReference =
        new TableReference()
            .setProjectId(datasetReference.getProjectId())
            .setDatasetId(datasetReference.getDatasetId())
            .setTableId(UUID.randomUUID().toString());

    // We finally write to the BigQuery table.
    org.apache.beam.sdk.io.gcp.bigquery.WriteResult result =
        input.apply(
            "Write To BigQuery",
            BigQueryIO.<Row>write()
                // This method delegates to Beam to automatically create the BigQuery table schema.
                .useBeamSchema()
                .withMethod(BigQueryIO.Write.Method.STREAMING_INSERTS)
                .withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(WriteDisposition.WRITE_APPEND)
                .to(tableReference));

    // We collect any failed insertion errors.
    PCollection<String> errors =
        result
            .getFailedInserts()
            .apply(
                "getFailedInsertsWithErr",
                MapElements.into(strings())
                    .via(failedTableRow -> requireNonNull(failedTableRow).toString()));

    // We collect successful insertions.
    PCollection<Row> success =
        result
            .getSuccessfulInserts()
            .apply(
                "successfulInserts",
                MapElements.into(rows())
                    .via(tableRow -> BigQueryUtils.toBeamRow(schema, requireNonNull(tableRow))))
            .setRowSchema(schema);

    // We finally collect the errors and successful inserts into a WriteResult.
    return WriteResult.of(input.getPipeline(), SUCCESS, success, ERROR, errors);
  }
}
