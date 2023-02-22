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
  name = var.subnetwork
  region = var.region
}
