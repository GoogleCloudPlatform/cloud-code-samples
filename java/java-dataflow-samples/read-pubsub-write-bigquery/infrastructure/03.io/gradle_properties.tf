output "gradle_properties" {
  value = templatefile("${path.module}/gradle.properties.tmpl", {
    subscription = module.infrastructure.source_pubsub
    dataset = module.infrastructure.sink_bigquery
    project_id = module.infrastructure.project_id
    region = module.infrastructure.region
    network = module.infrastructure.network
    subnetwork = module.infrastructure.subnetwork
    service_account_email = module.infrastructure.service_account_email
    temp_location = module.infrastructure.temp_location
  })
}
