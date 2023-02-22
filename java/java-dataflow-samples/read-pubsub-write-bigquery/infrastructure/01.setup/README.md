# Overview

This directory sets up the Google Cloud project environment for Dataflow usage.
It is recommended to apply the code in a temporary Google Cloud project
isolated from your production projects.

# List of all provision GCP resources

The following table lists all provisioned resources and their rationale.

| Resource                        | Reason                                     |
|---------------------------------|--------------------------------------------|
| API services                    | Required by GCP to provision resources     | 
| Dataflow Worker Service Account | Use GCP service account other than default | 
| Worker IAM Roles                | Follow principle of least privilege        |

# Usage

Follow terraform workflow convention to apply this module.

For example:
```
DIR=infrastructure/01.setup
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var=project=$(gcloud config get-value project)
```
