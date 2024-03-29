variable "project" {
  type        = string
  description = "The Google Cloud Platform (GCP) project within which resources are provisioned"
}

variable "region" {
  type        = string
  description = "The Google Cloud Platform (GCP) region in which to provision resources"
  default     = "us-central1"
}

variable "dataflow_worker_service_account_id" {
  type        = string
  description = "The Dataflow Worker Service Account ID"
  default     = "dataflow-worker"
}
