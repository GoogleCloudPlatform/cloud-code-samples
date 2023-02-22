// Provision virtual custom network
resource "google_compute_network" "default" {
  name                    = var.network_name_base
  auto_create_subnetworks = false
}

// Provision subnetwork of the virtual custom network
resource "google_compute_subnetwork" "default" {
  name                     = var.network_name_base
  ip_cidr_range            = var.subnetwork_cidr_range
  network                  = google_compute_network.default.name
  private_ip_google_access = true
  region                   = var.region
}

// Provision firewall rule for internal network traffic.
resource "google_compute_firewall" "default" {
  name    = "allow-data-pipelines-internal"
  network = google_compute_network.default.name

  allow {
    protocol = "tcp"
  }

  source_tags = ["dataflow"]
}
