# Contributing

Join us at https://github.com/GoogleCloudPlatform/cloud-code-samples.

## Run the Tests

Some of the samples are tested by a dedicated [Cloud Build](https://cloud.google.com/cloud-build)  configuration.

All sample tests are expected to take the following steps:

* Build the service
* Deploy to the intended platform (such as Cloud Run)
* Run the tests
* Delete the service, container image, and any other resources created during testing

### Cloud Run Hello World

Run the following from the `golang/` directory:

```sh
export SAMPLE=go-cloud-run-hello-world
gcloud builds submit $SAMPLE --config .ci/$SAMPLE.cloudbuild.yaml --substitutions COMMIT_SHA=manual
```

#### Running locally

You can run the tests against a locally running instance:

```sh
GOOGLE_CLOUD_PROJECT=local go run .
go test -v .
```
