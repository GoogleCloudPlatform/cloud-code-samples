# Copyright 2023 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

// Provision BigQuery dataset for use as sink
resource "google_bigquery_dataset" "sink" {
  dataset_id = local.bigquery.sink.dataset_id
}

resource "google_bigquery_dataset_iam_member" "sink" {
  dataset_id = google_bigquery_dataset.sink.dataset_id
  member     = "serviceAccount:${data.google_service_account.dataflow_worker.email}"
  role       = "roles/bigquery.dataEditor"
}