package com.cloudcode.dataflow.example.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.api.services.bigquery.model.DatasetReference;
import org.junit.jupiter.api.Test;

/** Tests for {@link DatasetReferenceOptionValue}. */
class DatasetReferenceOptionValueTest {

  @Test
  void withColonBasedDatasetReference() {
    String option = "aProjectId:aDatasetId";
    assertEquals(
        new DatasetReference().setProjectId("aProjectId").setDatasetId("aDatasetId"),
        new DatasetReferenceOptionValue(option).getValue());
  }

  @Test
  void withPeriodBasedDatasetReference() {
    String option = "aProjectId.aDatasetId";
    assertEquals(
        new DatasetReference().setProjectId("aProjectId").setDatasetId("aDatasetId"),
        new DatasetReferenceOptionValue(option).getValue());
  }

  @Test
  void inputErrorInformsValidFormat() {
    String missingDelimiter = "aProjectIdaDatasetId";
    IllegalArgumentException error =
        assertThrows(
            IllegalArgumentException.class,
            () -> new DatasetReferenceOptionValue(missingDelimiter));
    assertEquals(
        "input does not match BigQuery dataset pattern, "
            + "expected 'project_id.dataset_id' or 'project_id:dataset_id, got: aProjectIdaDatasetId",
        error.getMessage());
  }
}
