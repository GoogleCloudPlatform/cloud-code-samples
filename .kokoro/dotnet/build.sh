#!/bin/bash

# Fail on any error.
set -e

# Display commands being run
set -x

gcloud builds submit . --config github/cloud-code-samples/dotnet/dotnet-hello-world/.build/cloudbuild.gke.yaml

printenv