# Overview

This directory contains Infrastructure-as-Code (IaC) to provision dependent resources to execute the Beam pipeline on a
Dataflow. It is recommended to apply the code in a temporary Google Cloud project isolated from your production
projects.  It is expected for the user to apply this IaC using
the open in google cloud shell button found on the
[java/java-dataflow-samples/read-pubsub-write-bigquery/README](..)

# Requirements

- [Google Cloud SDK](https://cloud.google.com/sdk); `gcloud init`
  and `gcloud auth`
- Google Cloud project with billing enabled
- [terraform](https://www.terraform.io/)

# List of all provision GCP resources

See the README in each folder for a list of provisioned GCP resources
that result from applying its IaC.

# Usage
It is expected for the user to apply this IaC using
the open in google cloud shell button found on the
[java/java-dataflow-samples/read-pubsub-write-bigquery/README](..).

Nonetheless, if you would like to execute the terraform on your local machine,
each directory follows terraform workflow convention to apply modules.
Module directories are numbered in the recommended order of execution.

For example:
```
DIR=infrastructure/01.setup
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var=project=$(gcloud config get-value project)
```
