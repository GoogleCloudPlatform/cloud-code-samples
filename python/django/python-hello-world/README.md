# Hello World with Cloud Code

![Architecture Diagram](./img/diagram.png)

"Hello World" is a simple Kubernetes application that contains
[Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) for a web server and a database, and corresponding
[Services](https://kubernetes.io/docs/concepts/services-networking/service/). The Deployment contains a
[Django-based](https://www.djangoproject.com/) web server that simply prints "Hello World".

----

## Table of Contents

### Cloud Code for Visual Studio Code

1. [Getting Started](#getting-started])
2. What's in the box
    * .vscode
      * extensions.json: prompt requesting download of related extensions when opening this project
      * launch.json: information needed for the debugger to attach to our service
      * tasks.json: configuration information for Visual Studio Code Tasks
    * kubernetes-manifests
      * hello.deployment.yaml: Kubernetes manifest for the Hello World server Deployment
      * hello.service.yaml: Kubernetes manifest for the Hello World LoadBalancer Service
      * hello.migration.yaml: Kubernetes manifest for the Postgres database migration job
      * postgres.development.yaml: Kubernetes manifest for the Postgres database Deployment
      * postgres.service.yaml: Kubernetes manifest for the Postgres database Cluster IP Service
    * src
      * helloworld, manage.py: Django project files generated with "[django-admin startproject helloworld](https://docs.djangoproject.com/en/2.2/ref/django-admin/#startproject)".
      * home: A simple Django app that only prints "Hello World".
      * Dockerfile: used to build the container image for our program
    * skaffold.yaml: config file for [Skaffold](https://skaffold.dev/docs/), which is used by Cloud Code to build and deploy images
3. Using Cloud Code
    * [Set up a Google Kubernetes Engine Cluster](https://cloud.google.com/code/docs/vscode/quickstart#creating_a_google_kubernetes_engine_cluster)
    * [Deploy the app](https://cloud.google.com/code/docs/vscode/quickstart#deploying_your_app)
    * [Continuous Deployment](https://cloud.google.com/code/docs/vscode/quickstart#initiating_continuous_deployment)
    * [View Container Logs](https://cloud.google.com/code/docs/vscode/quickstart#viewing_container_logs)
    * [Debug Your Code](https://cloud.google.com/code/docs/vscode/quickstart#debugging_your_application)
    * [Open a Terminal in Your Container](https://cloud.google.com/code/docs/vscode/quickstart#opening_a_terminal_in_your_container)
4. [Using the Command Line](#using-the-command-line)
    * [Skaffold](#using-skaffold)
    * [kubectl](#using-kubectl)

----

### Getting Started

This sample was written to demonstrate how to use the Cloud Code extension for Visual Studio code.

Please change [SECRET_KEY](./src/helloworld/settings.py#L28) as soon as possible. If you keep
its value unchanged, your application will be vulnerable from security issues. See [link](https://docs.djangoproject.com/en/2.2/ref/settings/#secret-key) for more details.

* [Install Cloud Code for VS Code](https://cloud.google.com/code/docs/vscode/install)
* [Creating a new app](https://cloud.google.com/code/docs/vscode/creating-an-application)
* [Editing YAML files](https://cloud.google.com/code/docs/vscode/yaml-editing)

----

### Using the Command Line

As an alternative to using the Cloud Code extension, the application can be deployed to a cluster using standard command line tools

#### Skaffold

[Skaffold](https://github.com/GoogleContainerTools/skaffold) is a command line tool that can be used to build, push, and deploy your container images

```bash
skaffold run --default-repo=gcr.io/your-project-id-here/cloudcode
```

#### kubectl

[kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/) is the official Kubernetes command line tool. It can be used to deploy Kubernetes manifests to your cluster, but images must be build seperately using another tool (for example, using the [Docker CLI](https://docs.docker.com/engine/reference/commandline/cli/))

-----|------
