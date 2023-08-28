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

// Query the Google Project context
data "google_project" "default" {}

// Query the Dataflow Worker Service Account
data "google_service_account" "dataflow_worker" {
  account_id = var.dataflow_worker_service_account_id
}

// Query the Pub/Sub topic
data "google_pubsub_topic" "reference" {
  name    = var.reference_pubsub_topic.name
  project = var.reference_pubsub_topic.project
}

// Query the Google Cloud Network
data "google_compute_network" "default" {
  name = var.network
}

// Query the Google Cloud Subnetwork
data "google_compute_subnetwork" "default" {
  name   = var.subnetwork
  region = var.region
}
