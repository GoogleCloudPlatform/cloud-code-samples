// Query the Dataflow Worker Service Account
data "google_service_account" "dataflow_worker" {
  account_id = var.dataflow_worker_service_account_id
}