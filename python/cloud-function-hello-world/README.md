<img src="https://avatars2.githubusercontent.com/u/2810941?v=3&s=96" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# Cloud Functions Hello World with Cloud Code

"Python: Hello World" is a simple HTTP-triggered Cloud Functions application that contains a sample Python-based script that outputs a sample "Hello World" string.

## Table of Contents

* [Directory contents](#directory-contents)
* [Getting started with VS Code](#getting-started-with-vs-code)
* [Sign up for user research](#sign-up-for-user-research)

## Directory contents
* `launch.json` - the required configurations for your function
* `main.py` - the Python "Hello World" sample’s code
* `requirements.txt` - includes the functions framework dependency

## Getting started with VS Code

### Before you begin

1. If you're new to Google Cloud, [create an account](https://console.cloud.google.com/freetrial/signup/tos) to evaluate how our products perform in real-world scenarios. New customers also get $300 in free credits to run, test, and deploy workloads.

1. If you're testing this out to learn about the feature, [create a new project](https://pantheon.corp.google.com/projectselector2/home/dashboard) so that you can delete the project and all associated resources when you're finished.

   You can also use this template as a starting point to create a new function in a new or existing project.

1. Make sure that billing is enabled for your Cloud project. Learn how to [check if billing is enabled on a project](https://cloud.google.com/billing/docs/how-to/verify-billing-enabled).

1. [Enable the following APIs](https://pantheon.corp.google.com/projectselector2/apis/enableflow?apiid=cloudfunctions,cloudbuild.googleapis.com,artifactregistry.googleapis.com,run.googleapis.com,logging.googleapis.com,pubsub.googleapis.com&redirect=https:%2F%2Fcloud.google.com%2Ffunctions%2Fdocs%2Fcreate-deploy-nodejs):

    * Cloud Functions
    * Cloud Build
    * Artifact Registry
    * Cloud Run
    * Logging
    * Pub/Sub
    
1. Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git). Git is required for copying samples to your machine.

1. Install the [Cloud Code plugin](https://cloud.google.com/code/docs/vscode/install#installing) if you haven't already.

1. Since Cloud Functions integration is currently a pre-release feature, you'll also need to [install the pre-release build](https://cloud.google.com/code/docs/vscode/insiders#get).

#### Create a function

To create a new function using this sample, follow these steps:

1. Click ![Cloud Code icon](https://cloud.google.com/static/code/docs/vscode/images/cloudcode-icon.png) **Cloud Code** and then expand the **Cloud Functions** section.

1. Click **+ Create function** and select the **Python: Hello World** template.

1. Navigate to the pathway that you'd like to create your new function in, enter a name for the function, and select **Create New Application**.

1. If the folder of your application doesn't appear automatically in the **Explorer**, click ![VS Code Refresh icon](https://cloud.google.com/static/code/docs/vscode/images/refresh-icon.png) **Refresh**.

#### Deploy a function

To deploy a function, follow these steps:

1. Right-click a function and select **Deploy function**.

1. In the Quickpick menu, select a GCP project to deploy your function to.

1. Select a region that the function will be deployed to.

1. Select a runtime.

The function's deployment may take a few minutes.

If the deployment fails, refer to the **Output** tab for the error message. Clicking the link takes you to the build logs in Google Cloud console and provides more detail about the error.

#### Clean up

To delete only the function you created for this quickstart:

1. In the Cloud Functions explorer, right-click the function name and then select **Open in Cloud Console**.

1. Click **Delete** and then click **Delete**.

To delete your project and the project's associated resources:

1. Go to the [Projects page](https://pantheon.corp.google.com/cloud-resource-manager) in the Google Cloud console.

1. Select the project that you created for this quickstart and then click **Delete**.

1. Type the project ID to confirm and then click **Shut down**.

   This shuts down the project and schedules it for deletion.

### Sign up for user research

We want to hear your feedback!

The Cloud Code team is inviting our user community to sign-up to participate in Google User Experience Research. 

If you’re invited to join a study, you may try out a new product or tell us what you think about the products you use every day. At this time, Google is only sending invitations for upcoming remote studies. Once a study is complete, you’ll receive a token of thanks for your participation such as a gift card or some Google swag. 

[Sign up using this link](https://google.qualtrics.com/jfe/form/SV_4Me7SiMewdvVYhL?reserved=1&utm_source=In-product&Q_Language=en&utm_medium=own_prd&utm_campaign=Q1&productTag=clou&campaignDate=January2021&referral_code=UXbT481079) and answer a few questions about yourself, as this will help our research team match you to studies that are a great fit.
