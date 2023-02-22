variable "project" {
  type        = string
  description = "The Google Cloud Platform (GCP) project within which resources are provisioned"
}

variable "region" {
  type        = string
  description = "The Google Cloud Platform (GCP) region in which to provision resources"
  default     = "us-central1"
}

variable "network" {
  type        = string
  description = "The Google Cloud custom network"
  default     = "dataflow-pipeline"
}

variable "subnetwork" {
  type        = string
  description = "The Google Cloud custom subnetwork"
  default     = "data-pipeline"
}
variable "dataflow_worker_service_account_id" {
  type        = string
  description = "The Dataflow Worker Service Account ID"
  default     = "dataflow-worker"
}

variable "reference_pubsub_topic" {
  type = object({
    name    = string
    project = string
  })
  description = "The existing Pub/Sub topic to which we provision a subscription"
  default     = {
    name    = "taxirides-realtime"
    project = "pubsub-public-data"
  }
}
