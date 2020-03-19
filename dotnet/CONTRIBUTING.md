# Contributing

Join us at <https://github.com/GoogleCloudPlatform/cloud-code-samples.>

## Run the Tests

The samples are tested by a dedicated [Cloud Build](https://cloud.google.com/cloud-build)  configuration.

All sample tests are expected to take the following steps:

* Build the sample
* Deploy to relevant platform (e.g. Kubernetes, Cloud Run)
* Run the tests
* Delete everything the test created upon successful completion (e.g. service and container images)

### Cloud Run Hello World

Run the following from the `dotnet/` directory:

```sh
export SAMPLE=dotnet-cloud-run-hello-world
gcloud builds submit $SAMPLE --config .ci/$SAMPLE.cloudbuild.yaml --substitutions COMMIT_SHA=manual
```
