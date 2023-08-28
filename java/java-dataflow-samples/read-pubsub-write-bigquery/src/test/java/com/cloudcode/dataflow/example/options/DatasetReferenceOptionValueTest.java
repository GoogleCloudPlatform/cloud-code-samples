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
