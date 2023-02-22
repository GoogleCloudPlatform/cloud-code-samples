package com.cloudcode.dataflow.example.io;

import static java.util.Objects.requireNonNull;
import static org.apache.beam.sdk.values.TypeDescriptors.rows;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

import com.google.api.services.bigquery.model.DatasetReference;
import com.google.api.services.bigquery.model.TableReference;
import com.google.gson.Gson;
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

/** {@link WriteRowToBigquery} writes a {@link PCollection} and returns a {@link WriteResult}. */
public class WriteRowToBigquery extends PTransform<PCollection<Row>, WriteResult> {
  private static final Gson GSON = new Gson();
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
    Schema schema = input.getSchema();
    TableReference tableReference =
        new TableReference()
            .setProjectId(datasetReference.getProjectId())
            .setDatasetId(datasetReference.getDatasetId())
            .setTableId(UUID.randomUUID().toString());

    org.apache.beam.sdk.io.gcp.bigquery.WriteResult result =
        input.apply(
            "Write To BigQuery",
            BigQueryIO.<Row>write()
                .useBeamSchema()
                .withMethod(BigQueryIO.Write.Method.STREAMING_INSERTS)
                .withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(WriteDisposition.WRITE_APPEND)
                .to(tableReference));

    PCollection<String> errors =
        result
            .getFailedInserts()
            .apply(
                "getFailedInsertsWithErr",
                MapElements.into(strings())
                    .via(failedTableRow -> requireNonNull(failedTableRow).toString()));

    PCollection<Row> success =
        result
            .getSuccessfulInserts()
            .apply(
                "successfulInserts",
                MapElements.into(rows())
                    .via(tableRow -> BigQueryUtils.toBeamRow(schema, requireNonNull(tableRow))))
            .setRowSchema(schema);

    return WriteResult.of(input.getPipeline(), SUCCESS, success, ERROR, errors);
  }
}
