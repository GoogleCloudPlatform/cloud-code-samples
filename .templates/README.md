# Templates in Cloud Code

`template_location_v2.json` contains the information to pull sample templates from a GitHub repository.


* `repoPath`: Path to the GitHub repository containing the template.
* `directoryPath`: Since a repository can contain multiple templates, `directoryPath` is used to determine the location of the template in a repository.
* `templatePath`: Name of the folder where the template will be downloaded too.
* `templateName`: Name of the template.
* `templateLanguages`: The list of programming languages used by the template. Possible values currently include:
  * `python`
  * `go`
  * `java`
  * `nodejs`
  * `csharp`
* `runPlatforms`: The platforms where the template can be deployed. Possible values currently include:
  * `kubernetes`
  * `cloudrun`
