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

// Provision Storage Bucket for use by Dataflow Worker to read data
resource "google_storage_bucket" "temporary" {
  location = var.region
  name     = local.storage.temporary.bucket
  labels   = {
    purpose = "dataflow-job-storage"
  }
  uniform_bucket_level_access = true
}

// Enable Dataflow Worker Service Account to manage objects in sink bucket
resource "google_storage_bucket_iam_member" "temporary" {
  bucket = google_storage_bucket.temporary.id
  member = "serviceAccount:${data.google_service_account.dataflow_worker.email}"
  role   = "roles/storage.objectAdmin"
}
