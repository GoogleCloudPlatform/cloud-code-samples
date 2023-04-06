package com.cloudcode.dataflow.example.options;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.api.services.bigquery.model.DatasetReference;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parses Pipeline option value into a {@link DatasetReference}. */
public class DatasetReferenceOptionValue implements Serializable {

  // For parsing the format used to parse a String into a dataset reference.
  // "{project_id}:{dataset_id}" or
  // "{project_id}.{dataset_id}"
  private static final Pattern DATASET_PATTERN =
      Pattern.compile("^(?<PROJECT>[^\\.:]+)[\\.:](?<DATASET>[^\\.:]+)$");

  private final DatasetReference datasetReference;

  DatasetReferenceOptionValue(String input) {
    Matcher m = DATASET_PATTERN.matcher(input);
    checkArgument(
        m.matches(),
        "input does not match BigQuery dataset pattern, "
            + "expected 'project_id.dataset_id' or 'project_id:dataset_id, got: %s",
        input);
    this.datasetReference =
        new DatasetReference().setProjectId(m.group("PROJECT")).setDatasetId(m.group("DATASET"));
  }

  public DatasetReference getValue() {
    return datasetReference;
  }
}
