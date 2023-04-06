# Overview

This directory provisions Google Cloud project networking for Dataflow usage.
It is recommended to apply the code in a temporary Google Cloud project
isolated from your production projects.

# List of all provision GCP resources

The following table lists all provisioned resources and their rationale.

| resource       | reason                                      |
|----------------|---------------------------------------------|
| Network        | Run workload in its isolated GCP VPC        |
| Subnetwork     | Worker needs at least one subnetwork        |
| Firewall Rules | Limit traffic to Worker service account VMS |

# Usage

Follow terraform workflow convention to apply this module.

For example:
```
DIR=infrastructure/02.network
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var=project=$(gcloud config get-value project)
```
