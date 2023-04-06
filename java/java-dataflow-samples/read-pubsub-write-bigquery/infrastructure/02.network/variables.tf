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

variable "network_name_base" {
  type        = string
  description = "The name of the Google Cloud Platform (GCP) name basis from which we name network related resources"
  default     = "dataflow-pipeline"
}

variable "subnetwork_cidr_range" {
  type        = string
  description = "The address range for this subnet, in CIDR notation. Use a standard private VPC network address range: for example, 10.0.0.0/9."
  default     = "10.128.0.0/20"
}
