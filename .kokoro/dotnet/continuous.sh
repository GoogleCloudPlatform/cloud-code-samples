#!/bin/bash

# Fail on any error.
set -e

# Display commands being run
set -x

export GCLOUD_PROJECT=cloud-code-samples-tests

# Get skaffold
curl -Lo skaffold https://storage.googleapis.com/skaffold/builds/latest/skaffold-linux-amd64
chmod +x skaffold
export PATH=$PATH:$(pwd)

cd github/cloud-code-samples/dotnet

# Building locally using skaffold
skaffold build --default-repo gcr.io/${GCLOUD_PROJECT}

