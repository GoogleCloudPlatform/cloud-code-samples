<img src="https://avatars2.githubusercontent.com/u/2810941?v=3&s=96" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# Simple application to interact with Gemini API

"NodeJS: Gemini API" is a simple sample application that shows you how to interact with Google's Gemini APIs .

## Table of Contents

* [Directory contents](#directory-contents)
* [Setting up the API Key](#setting-up-the-api-key)
* [Getting started](#getting-started-with-vs-code)
* [Sign up for user research](#sign-up-for-user-research)

## Directory contents
* `launch.json` - config file for later when you deploy your application to Google Cloud 
* `index.js` - the Node sample application that asks Gemini API to generate content based on a prompt
* `package.json` - includes the google generative ai dependency

## Setting up the API Key
Before you can use the Gemini API, you must first obtain an API key. If you don't already have one, create a key with one click in Google AI Studio.
[Get API](https://makersuite.google.com/app/apikey)

## Getting started with Cloud Code

### Run the application locally 

1. Make sure you have generated the API key as shown above. Please make sure to use and store this key securely. 

1. Install the package using 
```npm install @google/generative-ai```

1. Run this using 
```node index.js```

### Documentation 
1. You can see detailed API Reference for the Gemini APIs [here](https://googledevai.google.com/api) 

1. You can see more samples and things to do [here](https://googledevai.google.com/tutorials/python_quickstart) 

### Other things to try 
    
1. If you're new to Google Cloud, [create an account](https://console.cloud.google.com/freetrial/signup/tos) to evaluate how our products perform in real-world scenarios. New customers also get $300 in free credits to run, test, and deploy workloads.

1. Install the Cloud Code [VS Code plugin](https://cloud.google.com/code/docs/vscode/install#installing) or [Jetbrains Extension](https://cloud.google.com/code/docs/intellij/install) if you haven't already.

1. Access Cloud Code [documentation](https://cloud.google.com/code/docs/) to learn how you can deploy your app to Google Cloud 

### Sign up for user research

We want to hear your feedback!

The Cloud Code team is inviting our user community to sign-up to participate in Google User Experience Research. 

If you’re invited to join a study, you may try out a new product or tell us what you think about the products you use every day. At this time, Google is only sending invitations for upcoming remote studies. Once a study is complete, you’ll receive a token of thanks for your participation such as a gift card or some Google swag. 

[Sign up using this link](https://google.qualtrics.com/jfe/form/SV_4Me7SiMewdvVYhL?reserved=1&utm_source=In-product&Q_Language=en&utm_medium=own_prd&utm_campaign=Q1&productTag=clou&campaignDate=January2021&referral_code=UXbT481079) and answer a few questions about yourself, as this will help our research team match you to studies that are a great fit.
