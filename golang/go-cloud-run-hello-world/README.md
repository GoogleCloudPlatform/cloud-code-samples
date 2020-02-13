# Cloud Run Hello World with Cloud Code

This "Hello World" is a [Cloud Run](https://cloud.google.com/run/docs) service that renders a webpage.

----
## Table of Contents

### Cloud Code for Visual Studio Code

1. [Getting Started](#getting-started])
2. [What's in the box](https://cloud.google.com/code/docs/vscode/quickstart#whats_in_the_box)
3. [Using the Command Line](#using-the-command-line)

----

## Getting Started

This sample was written to demonstrate how to use the Cloud Code extension for Visual Studio code.

* [Install Cloud Code for VS Code](https://cloud.google.com/code/docs/vscode/install)
* [Creating a new app](https://cloud.google.com/code/docs/vscode/creating-an-application)
* [Editing YAML files](https://cloud.google.com/code/docs/vscode/yaml-editing)

## Using the Command Line

## Build the Container Image

```
docker build -t go-cloud-run-hello-world .
```

## Run the Container Locally

```
docker run ...
```

## Deploy to Cloud Run

```
gcloud builds submit --tag gcr.io/$PROJECT_ID/go-cloud-run-hello-world
gcloud run deploy hello-world \
  --image gcr.io/$PROJECT_ID/go-cloud-run-hello-world \
  --allow-unauthenticated \
  --platform managed \
  --region us-central1 \
```

## Run the Tests

```
gcloud builds submit . --config cloudbuild.yaml --substitutions COMMIT_SHA=manual
```