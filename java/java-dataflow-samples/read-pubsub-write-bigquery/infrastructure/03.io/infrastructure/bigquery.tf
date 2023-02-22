// Provision BigQuery dataset for use as sink
resource "google_bigquery_dataset" "sink" {
  dataset_id = local.bigquery.sink.dataset_id
}
