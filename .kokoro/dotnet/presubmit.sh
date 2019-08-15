#!/bin/bash

# Copyright 2018 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Fail on any error.
set -e

# Display commands being run
set -x

export PATH=${PATH}:${HOME}/gcloud/google-cloud-sdk/bin
export GCLOUD_PROJECT=cloud-code-samples-tests
export GOOGLE_APPLICATION_CREDENTIALS=${KOKORO_GFILE_DIR}/cloud-code-samples-tests-ac6e20d34ba6.json

# Activate service account used for invoking CI/CD builds with Cloud Build
gcloud auth activate-service-account --project=$GCLOUD_PROJECT --key-file="$GOOGLE_APPLICATION_CREDENTIALS" --quiet
#gcloud init --console-only 
gcloud config set project $GCLOUD_PROJECT
gcloud config set account cicd-983@cloud-code-samples-tests.iam.gserviceaccount.com
#gcloud auth application-default login --no-launch-browser --quiet

# Submit a build job
gcloud builds submit . --config github/cloud-code-samples/dotnet/dotnet-hello-world/.build/cloudbuild.gke.yaml --project=$GCLOUD_PROJECT

printenv