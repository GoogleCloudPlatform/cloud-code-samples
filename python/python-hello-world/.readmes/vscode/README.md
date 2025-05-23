# Kubernetes Hello World with Cloud Code

This "Hello World" sample demonstrates how to deploy a simple "Hello World" application to Kubernetes using the Cloud Code extension for Visual Studio Code. When you run the application, Cloud Code uses [skaffold](https://skaffold.dev/docs/) under the hood to build an image and deploy the project's Kubernetes manifests. To learn more about Kubernetes, explore the [Kubernetes overview](https://kubernetes.io/docs/concepts/overview/). 

### Table of Contents
* [What's in this sample](#whats-in-this-sample)
* [Getting Started](#getting-started)
    1. [Set up a Kubernetes cluster](#set-up-a-kubernetes-cluster)
        * [Minikube](#minikube) - free local cluster
        * [GKE](#GKE) - Google Kubernetes Engine
    2. [Deploy app to cluster](#deploy-app-to-cluster)
* [Next steps](#next-steps)
* [Sign up for user research](#sign-up-for-user-research)

---
## What's in this sample
### Kubernetes architecture
![Kubernetes Architecture Diagram](../../img/diagram.png)

### Directory contents

- `skaffold.yaml` - A schema file that defines skaffold configurations ([skaffold.yaml reference](https://skaffold.dev/docs/references/yaml/))
- `kubernetes-manifests/` - Contains Kubernetes YAML files for the Guestbook services and deployments, including:

  - `hello.deployment.yaml` - deploys a pod with the 'python-hello-world' container image
  - `hello.service.yaml` - creates a load balancer and exposes the 'python-hello-world' service on an external IP in the cluster

---
## Getting Started

### Set up a Kubernetes cluster

#### Minikube
 
 Cloud Code uses [minikube](https://minikube.sigs.k8s.io/docs/) to create a free local cluster.

1. Navigate to the **Clusters** explorer in the Cloud Code - Kubernetes sidebar using the left side Activity bar.

> Note: in newer versions of VS Code, the **Clusters** explorer has been replaced with a dedicated **Kubernetes** section.

2. Click '+' in the title bar of the Clusters explorer to create a new cluster. If prompted, follow the instructions to log in to Google Cloud Platform.  

3. Choose **Minikube** and then select **minikube**. 

2. Select **Start**. Cloud Code will initiate a minikube cluster.

#### Google Kubernetes Engine

1. Navigate to the **Clusters** explorer in the Cloud Code - Kubernetes sidebar using the left side Activity bar.

> Note: in newer versions of VS Code, the **Clusters** explorer has been replaced with a dedicated **Kubernetes** section.

2. Click '+' in the title bar of the Clusters explorer to create a new cluster. If prompted, follow the instructions to log in to Google Cloud Platform.  

3. Choose **Google Kubernetes Engine**. If you have existing clusters associated with your GCP project, you can select one from this dialog. To create a new cluster, click **+ Create a new GKE Cluster** and follow these steps:

    a. Choose **Standard** or **Autopilot**. For more information, see [Comparing Autopilot and Standard modes](https://cloud.google.com/kubernetes-engine/docs/concepts/autopilot-overview#comparison?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-).

    b. If prompted, click **Open** to permit Cloud Code to open the Cloud Console.

    c. In the Cloud Console, choose any configuration options that you want to customize and then click **Create**.

    d. Once the cluster has finished being created, return to VS Code and click **Refresh**.

    You can now select your newly created cluster.

8. The cluster you select will be added to the **Clusters** explorer and set as the default context. You can inspect the cluster's properties, make changes to the cluster's resources, and view logs by clicking the dropdown arrow next to the cluster name in the **Clusters** explorer.

### Deploy app to cluster

1. Navigate to the **Development Sessions** explorer in the **Cloud Code - Kubernetes** sidebar using the Activity bar on the left side of the IDE.

2. Click the 'Play' icon in the title bar of the **Development Sessions** explorer to run the application. If prompted, confirm the current context and image registry.

3. View the build progress in the **Development Sessions** explorer. Once the build has finished, the URLs for your deployed app will be displayed under **Port Forward URLs**. Click on the 'Open Window' icon next to the service's URL to visit your deployed app.

4.  To stop the application, click the stop icon on the Debug Toolbar.

If you created a GKE cluster for this tutorial, be sure to delete your cluster to avoid incurring charges.

---
## Next steps
* Try [debugging your app](https://cloud.google.com/code/docs/vscode/debug?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-) using Cloud Code
* Navigate the [Kubernetes Engine Explorer](https://cloud.google.com/code/docs/vscode/k8s-overview?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-)
* Learn how to [edit YAML files](https://cloud.google.com/code/docs/vscode/yaml-editing?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-) to deploy your Kubernetes app
* [Configure an existing app](https://cloud.google.com/code/docs/vscode/setting-up-an-existing-app?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-) to run on Cloud Code
* Enable [Cloud APIs and client libraries](https://cloud.google.com/code/docs/vscode/client-libraries?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-)
* Manage secrets with [Secret Manager](https://cloud.google.com/code/docs/vscode/secret-manager?utm_source=ext&utm_medium=partner&utm_campaign=CDR_kri_gcp_cloudcodereadmes_012521&utm_content=-)

For more Cloud Code tutorials and resources, check out [Awesome Cloud Code](https://github.com/russwolf/awesome-cloudclode)!

---
## Sign up for User Research

We want to hear your feedback!

The Cloud Code team is inviting our user community to sign-up to participate in Google User Experience Research. 

If you’re invited to join a study, you may try out a new product or tell us what you think about the products you use every day. At this time, Google is only sending invitations for upcoming remote studies. Once a study is complete, you’ll receive a token of thanks for your participation such as a gift card or some Google swag. 

[Sign up using this link](https://google.qualtrics.com/jfe/form/SV_4Me7SiMewdvVYhL?reserved=1&utm_source=In-product&Q_Language=en&utm_medium=own_prd&utm_campaign=Q1&productTag=clou&campaignDate=January2021&referral_code=UXbT481079) and answer a few questions about yourself, as this will help our research team match you to studies that are a great fit.

----
