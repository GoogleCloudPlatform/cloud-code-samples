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
