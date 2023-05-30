// Provision subscription to the Pub/Sub topic
resource "google_pubsub_subscription" "source" {
  name  = data.google_pubsub_topic.reference.name
  topic = data.google_pubsub_topic.reference.id
}

// Allow Dataflow Worker Service Account to subscribe to Pub/Sub subscription
resource "google_pubsub_subscription_iam_member" "source" {
  for_each = toset([
    "roles/pubsub.viewer",
    "roles/pubsub.subscriber",
  ])
  member       = "serviceAccount:${data.google_service_account.dataflow_worker.email}"
  role         = each.key
  subscription = google_pubsub_subscription.source.id
}
