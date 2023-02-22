# Overview

This directory contains Infrastructure-as-Code (IaC) to provision dependent resources to execute the Beam pipeline on a
Dataflow. It is recommended to apply the code in a temporary Google Cloud project isolated from your production
projects.

# Easy Walkthrough 🏖️

For an easy walkthrough without installing anything on your local machine:

# Requirements

- [Google Cloud SDK](https://cloud.google.com/sdk); `gcloud init`
  and `gcloud auth`
- Google Cloud project with billing enabled
- [terraform](https://www.terraform.io/)

# List of all provision GCP resources

See the README in each folder for a list of provisioned GCP resources
that result from applying its IaC.

# Usage

Each directory follows terraform workflow convention to apply modules.
Module directories are numbered in the recommended order of execution.

For example:
```
DIR=infrastructure/01.setup
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var=project=$(gcloud config get-value project)
```
