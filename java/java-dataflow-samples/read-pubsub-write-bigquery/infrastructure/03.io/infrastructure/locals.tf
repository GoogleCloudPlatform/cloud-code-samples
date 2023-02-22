resource "random_string" "id" {
  length = 6
  upper = false
  special = false
}

locals {
  bigquery = {
    sink = {
      dataset_id = "sink_${random_string.id.result}"
    }
  }
  pubsub = {
    source = {
      name = "${data.google_pubsub_topic.reference.name}-source-${random_string.id.result}"
    }
  }
  storage = {
    temporary = {
      bucket = "temp-${random_string.id.result}"
    }
  }
}
