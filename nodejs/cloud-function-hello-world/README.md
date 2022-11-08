<img src="https://avatars2.githubusercontent.com/u/2810941?v=3&s=96" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# Cloud Functions Hello World with Cloud Code

"Node.js: Hello World" is a simple HTTP-triggered Cloud Functions application that contains a sample Node.js-based script that outputs a sample "Hello World" string.

For details on how to use this sample as a template in Cloud Code, see the [Create and deploy a function](https://cloud.google.com/code/docs/vscode/create-deploy-function) quickstart for VS Code.

## Table of Contents

* [Directory contents](#directory-contents)
* [Getting started with VS Code](#getting-started-with-vs-code)
* [Sign up for user research](#sign-up-for-user-research)

## Directory contents
* `launch.json` - the required configurations for your function
* `index.js` - the Node.js “Hello World” sample’s code
* `package.json` - includes the functions framework dependency

## Getting started with VS Code

### Before you begin

For prerequisite steps, see the [Create and deploy a function](https://cloud.google.com/code/docs/vscode/create-deploy-function#before_you_begin) quickstart.

#### Create a function

To create a new function using this sample, follow these steps:

1. Click ![Cloud Code icon](https://cloud.google.com/static/code/docs/vscode/images/cloudcode-icon.png) **Cloud Code** and then expand the **Cloud Functions** section.

1. Click **+ Create function** and select the **Node.js: Hello World** template.

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

### Sign up for user research

We want to hear your feedback!

The Cloud Code team is inviting our user community to sign-up to participate in Google User Experience Research. 

If you’re invited to join a study, you may try out a new product or tell us what you think about the products you use every day. At this time, Google is only sending invitations for upcoming remote studies. Once a study is complete, you’ll receive a token of thanks for your participation such as a gift card or some Google swag. 

[Sign up using this link](https://google.qualtrics.com/jfe/form/SV_4Me7SiMewdvVYhL?reserved=1&utm_source=In-product&Q_Language=en&utm_medium=own_prd&utm_campaign=Q1&productTag=clou&campaignDate=January2021&referral_code=UXbT481079) and answer a few questions about yourself, as this will help our research team match you to studies that are a great fit.
