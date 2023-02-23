// Provision BigQuery dataset for use as sink
resource "google_bigquery_dataset" "sink" {
  dataset_id = local.bigquery.sink.dataset_id
}

resource "google_bigquery_dataset_iam_member" "sink" {
  dataset_id = google_bigquery_dataset.sink.dataset_id
  member     = "serviceAccount:${data.google_service_account.dataflow_worker.email}"
  role       = "roles/bigquery.dataEditor"
}