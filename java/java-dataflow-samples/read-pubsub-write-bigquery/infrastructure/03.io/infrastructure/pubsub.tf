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

// Provision subscription to the Pub/Sub topic
resource "google_pubsub_subscription" "source" {
  name  = data.google_pubsub_topic.reference.name
  topic = data.google_pubsub_topic.reference.id
}

// Allow Dataflow Worker Service Account to subscribe to Pub/Sub subscription
resource "google_pubsub_subscription_iam_member" "source" {
  for_each = toset([
    "roles/pubsub.viewer",
    "roles/pubsub.subscriber",
  ])
  member       = "serviceAccount:${data.google_service_account.dataflow_worker.email}"
  role         = each.key
  subscription = google_pubsub_subscription.source.id
}
