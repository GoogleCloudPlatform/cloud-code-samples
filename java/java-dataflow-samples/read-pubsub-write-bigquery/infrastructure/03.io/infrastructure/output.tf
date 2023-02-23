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

output "temp_location" {
  value = "gs://${google_storage_bucket.temporary.name}/temp"
}
