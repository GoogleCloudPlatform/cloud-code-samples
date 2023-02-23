// Provision a service account that will be bound to the Dataflow pipeline
resource "google_service_account" "dataflow_worker" {
  account_id   = var.dataflow_worker_service_account_id
  display_name = var.dataflow_worker_service_account_id
  description  = "The service account bound to the compute engine instance provisioned to run Dataflow Jobs"
}

// Provision IAM roles for the Dataflow runner service account
resource "google_project_iam_member" "dataflow_worker_service_account_roles" {
  depends_on = [google_project_service.required_services]
  for_each   = toset([
    "roles/dataflow.worker",
  ])
  role    = each.key
  member  = "serviceAccount:${google_service_account.dataflow_worker.email}"
  project = var.project
}
