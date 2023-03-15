// Provision the required Google Cloud services
resource "google_project_service" "required_services" {
  for_each = toset([
    "dataflow",
    "compute",
    "pubsub"
  ])

  service            = "${each.key}.googleapis.com"
  disable_on_destroy = false
}