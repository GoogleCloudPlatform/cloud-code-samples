#!/bin/bash

# Fail on any error.
set -e

# Display commands being run
set -x

export ProjectId=cloud-code-samples-tests

# Activate service account used for invoking CI/CD builds with Cloud Build
gcloud auth activate-service-account --project=$ProjectId --key-file=cloud-code-samples-tests-ac6e20d34ba6.json
gcloud init --console-only 
gcloud config set account cicd-983@cloud-code-samples-tests.iam.gserviceaccount.com
gcloud auth application-default login --no-launch-browser

# Submit a build job
gcloud builds submit . --config github/cloud-code-samples/dotnet/dotnet-hello-world/.build/cloudbuild.gke.yaml

printenv