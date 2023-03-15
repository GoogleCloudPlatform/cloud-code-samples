# Overview

This directory provisions Google Cloud project resources of which the
Apache Beam pipeline will read from and write to.
It is recommended to apply the code in a temporary Google Cloud project
isolated from your production projects.

# List of all provision GCP resources

The following lists all provisioned resources and their rationale
categorized by GCP service.

| resource                    | reason                           |
|-----------------------------|----------------------------------|
| Pub/Sub subscription        | Intended as a source             |
| Pub/Sub topic               | Intended as a sink               |
| Google Cloud Storage Bucket | Intended as both source and sink |
| 2 BigQuery Datasets         | Intended as a source and sink    |
| BigQuery Table              | Intended as a source             |

# Usage

Follow terraform workflow convention to apply this module.

For example:
```
DIR=infrastructure/03.io
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var=project=$(gcloud config get-value project)
```
