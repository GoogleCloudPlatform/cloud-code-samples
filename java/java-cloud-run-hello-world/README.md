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
* [Service Configuration](#service-configuration)
* [Next steps](#next-steps)
* [Contributing](#contributing)
----

## VS Code Guide

### Getting Started

This sample demonstrates how to use the Cloud Code extension in VS Code.

* [Install Cloud Code for VS Code](https://cloud.google.com/code/docs/vscode/install)
* [Creating a new app](https://cloud.google.com/code/docs/vscode/creating-an-application)
* [Editing YAML files](https://cloud.google.com/code/docs/vscode/yaml-editing)

## Running locally

1. Open the command palette
2. Run `Cloud Code: Run Locally`

## Running on Cloud Run

1. Open the command palette
2. Run `Cloud Code: Deploy to Cloud Run`

## IntelliJ Guide

### Getting Started

This sample demonstrates how to use the Cloud Code extension in IntelliJ.

* [Install Cloud Code for IntelliJ](https://cloud.google.com/code/docs/intellij/install)
* [Creating a new app](https://cloud.google.com/code/docs/intellij/create-run-app)

## Service Configuration

Configuration for this service uses environment variables.

* **`GOOGLE_CLOUD_PROJECT`** [default: `<none>`] Override for the Project ID. If set the service assumes it's running locally and does not use the metadata server.
* **`PORT`** [default: `8080`] The service binds this port. To avoid conflicts, set explicitly set this environment variable to an unused value.

## Next Steps

* Read the Cloud Run documentation on [developing your service](https://cloud.google.com/run/docs/developing).
* Follow the [System packages tutorial](https://cloud.google.com/run/docs/tutorials/system-packages) to learn how to use the command-line to build and deploy a more complicated service.

### Run the Tests

The tests for this code are implemented as a [Cloud Build](https://cloud.google.com/cloud-build) pipeline which takes the following steps:

* Build the service
* Deploy to Cloud Run
* Run the tests
* Delete the service and container image

To run this end-to-end test manually, run this command-line operation:

```
gcloud builds submit . --config cloudbuild.yaml --substitutions COMMIT_SHA=manual
```

You can run the tests against a locally running instance of the service:

```
GOOGLE_CLOUD_PROJECT=local mvn spring-boot:run
mvn verify
```
