# Overview

The purpose of this walkthrough is to provision required resources to run the sample.  It should only take a few
minutes to complete.

## Prerequisite

In order to apply the terraform code to provision resources, you need to select or create a project.
**It is recommended to go through this walkthrough using a new temporary Google Cloud project, unrelated to any of your
existing Google Cloud projects.**

### 1. Select or create a project to begin.

<walkthrough-project-setup></walkthrough-project-setup>

### 2. Configure gcloud with the default project

```sh
gcloud config set project <walkthrough-project-id/>
```

Select the start button when you are ready.

## 1. Setup environment

Best practice recommends a Dataflow job to:
1) Utilize a worker service account to access the pipeline's files and resources
2) Bind minimally necessary IAM permissions for the worker service account
3) Use minimally required Google cloud services

Therefore, this step will:

- Create service accounts
- Provision IAM credentials
- Enable required Google cloud services

Run the terraform workflow in
the [infrastructure/01.setup](infrastructure/01.setup) directory.

Terraform will ask your permission before provisioning resources.
If you agree with terraform provisioning resources,
type `yes` to proceed.

```sh
DIR=infrastructure/01.setup
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var='project=<walkthrough-project-id/>'
```

## 2. Provision network

Best practice recommends a Dataflow job to:
1. Utilize a custom network and subnetwork
2. Configure minimally necessary network firewall rules

Therefore, this step will:

- Provision a custom network and subnetwork
- Provision firewall rules

Run the terraform workflow in
the [infrastructure/02.network](infrastructure/02.network) directory.

Terraform will ask your permission before provisioning resources.
If you agree with terraform provisioning resources,
type `yes` to proceed.

```sh
DIR=infrastructure/02.network
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var='project=<walkthrough-project-id/>'
```

## 3. Provision source and sink resources

To use realistic enough data, we create a subscription from the public Pub/Sub topic
`projects/pubsub-public-data/topics/taxirides-realtime`.  To provision a destination for processed data, we
create a BigQuery dataset.  Note that we do not need to create a table and rely on the Beam pipeline to do that
for us.  Additionally, we need a temporary storage bucket.

Therefore, this step will:
- Provision a Pub/Sub subscription of Pub/Sub topic `projects/pubsub-public-data/topics/taxirides-realtime` (Note:
your subscription will be private)
- Provision a BigQuery dataset
- Provision a Google Cloud Storage bucket

Run the terraform workflow in
the [infrastructure/03.io](infrastructure/03.io) directory.

Terraform will ask your permission before provisioning resources.
If you agree with terraform provisioning resources,
type `yes` to proceed.

```sh
DIR=infrastructure/03.io
terraform -chdir=$DIR init
terraform -chdir=$DIR apply -var='project=<walkthrough-project-id/>'
```

## 4. That's it 🏖️

Now that you have all the provisioned resources, run the following command to download a `gradle.properties` file
onto your local machine.  Move this file to the java/java-dataflow-samples/read-pubsub-write-bigquery
on your local machine.

```sh
DIR=infrastructure/03.io
terraform -chdir=$DIR output -raw gradle_properties > gradle.properties
cloudshell download-file gradle.properties
```
