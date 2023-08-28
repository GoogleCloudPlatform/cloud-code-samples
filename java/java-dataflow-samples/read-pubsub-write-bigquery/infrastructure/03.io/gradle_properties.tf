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

// Store gradle.properties in a Google Cloud storage bucket
resource "google_storage_bucket_object" "gradle_properties" {
  bucket  = module.infrastructure.storage_bucket
  name    = "gradle.properties"
  content = templatefile("${path.module}/gradle.properties.tmpl", {
    subscription          = module.infrastructure.source_pubsub
    dataset               = module.infrastructure.sink_bigquery
    project_id            = module.infrastructure.project_id
    region                = module.infrastructure.region
    network               = module.infrastructure.network
    subnetwork            = module.infrastructure.subnetwork
    service_account_email = module.infrastructure.service_account_email
    temp_location         = module.infrastructure.temp_location
  })
}

// Export the gradle.properties Google Cloud storage object URI.
output "gradle_properties" {
  value = "gs://${google_storage_bucket_object.gradle_properties.bucket}/${google_storage_bucket_object.gradle_properties.name}"
}
