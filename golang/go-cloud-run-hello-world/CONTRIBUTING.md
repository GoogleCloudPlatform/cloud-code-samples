# Contributing

Join us at https://github.com/GoogleCloudPlatform/cloud-code-samples.

## Run the Tests

The tests for this code are implemented as a [Cloud Build](https://cloud.google.com/cloud-build) pipeline. which takes the following steps:

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
GOOGLE_CLOUD_PROJECT=local go run .
go test -v .
```