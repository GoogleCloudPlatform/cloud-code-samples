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

output "network" {
  value = data.google_compute_network.default.name
}

output "subnetwork" {
  value = data.google_compute_subnetwork.default.name
}

output "project_id" {
  value = data.google_project.default.project_id
}

output "region" {
  value = var.region
}

output "service_account_email" {
  value = data.google_service_account.dataflow_worker.email
}

output "source_pubsub" {
  value = google_pubsub_subscription.source.id
}

output "sink_bigquery" {
  value = "${google_bigquery_dataset.sink.project}.${google_bigquery_dataset.sink.dataset_id}"
}

output "storage_bucket" {
  value = google_storage_bucket.temporary.name
}

output "temp_location" {
  value = "gs://${google_storage_bucket.temporary.name}/temp"
}
