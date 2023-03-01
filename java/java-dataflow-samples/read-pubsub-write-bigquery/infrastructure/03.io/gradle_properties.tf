// Store gradle.properties in a Google Cloud storage bucket
resource "google_storage_bucket_object" "gradle_properties" {
  bucket = module.infrastructure.storage_bucket
  name   = "gradle.properties"
  content = templatefile("${path.module}/gradle.properties.tmpl", {
    subscription = module.infrastructure.source_pubsub
    dataset = module.infrastructure.sink_bigquery
    project_id = module.infrastructure.project_id
    region = module.infrastructure.region
    network = module.infrastructure.network
    subnetwork = module.infrastructure.subnetwork
    service_account_email = module.infrastructure.service_account_email
    temp_location = module.infrastructure.temp_location
  })
}

// Export the gradle.properties Google Cloud storage object URI.
output "gradle_properties" {
  value = "gs://${google_storage_bucket_object.gradle_properties.bucket}/${google_storage_bucket_object.gradle_properties.name}"
}
