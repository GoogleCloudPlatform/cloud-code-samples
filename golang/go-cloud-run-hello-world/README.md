# Cloud Run Hello World with Cloud Code

This "Hello World" is a [Cloud Run](https://cloud.google.com/run/docs) service that renders a webpage.

----
## Table of Contents

* [VS Code Guide](#vs-code-guide)
    1. [Getting Started](#getting-started])
    2. [Running locally](#running-locally)
    3. [Running on Cloud Run](#running-on-cloud-run)
* [IntelliJ Guide](#intellij-guide)
    1. [Getting Started](#getting-started])
    2. [Running locally](#running-locally)
    3. [Running on Cloud Run](#running-on-cloud-run)
* [Next steps](#next-steps)
----

## VS Code Guide

### Getting Started

This sample demonstrates how to use the Cloud Code extension in VS Code.

* [Install Cloud Code for VS Code](https://cloud.google.com/code/docs/vscode/install)
* [Creating a new app](https://cloud.google.com/code/docs/vscode/creating-an-application)
* [Editing YAML files](https://cloud.google.com/code/docs/vscode/yaml-editing)

## Running locally

1. Open the command pallette
2. Run `Cloud Code: Run Locally`

## Running on Cloud Run

1. Open the command pallette
2. Run `Cloud Code: Deploy to Cloud Run`

## IntelliJ Guide

### Getting Started

This sample demonstrates how to use the Cloud Code extension in IntelliJ.

* [Install Cloud Code for IntelliJ](https://cloud.google.com/code/docs/intellij/install)
* [Creating a new app](https://cloud.google.com/code/docs/intellij/create-run-app)

## Next Steps

* Read the Cloud Run documentation on [developing your service](https://cloud.google.com/run/docs/developing).
* Follow the [System packages tutorial](https://cloud.google.com/run/docs/tutorials/system-packages) to learn how to use the command-line to build and deploy a more complicated service.
* {Cloud Code link?}

## Contributing

Join us at https://github.com/GoogleCloudPlatform/cloud-code-samples.

### Run the Tests

The tests for this code are implemented as a [Cloud Build](https://cloud.google.com/cloud-build) pipeline. To run the test manually, run this command-line operation:

```
gcloud builds submit . --config cloudbuild.yaml --substitutions COMMIT_SHA=manual
```