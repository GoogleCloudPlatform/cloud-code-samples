// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package main

import (
	"html/template"
	"log"
	"net/http"
	"os"

	"cloud.google.com/go/compute/metadata"
)

// templateData provides template parameters.
type templateData struct {
	Service      string
	Revision     string
	Project      string
	ProjectFound bool
}

// Variables used to generate the HTML page.
var (
	data templateData
	tmpl *template.Template
)

func main() {
	// Comment out in local development for timestamp prefixes.
	log.SetFlags(0)

	// Initialize template parameters.
	service := os.Getenv("K_SERVICE")
	if service == "" {
		service = "???"
	}

	revision := os.Getenv("K_REVISION")
	if revision == "" {
		revision = "???"
	}

	project := "???"
	projectFound := false
	// Only attempt to check the Cloud Run metadata server if it looks like
	// the service is deployed to Cloud Run.
	if service != "???" {
		var err error
		if project, err = metadata.ProjectID(); err != nil {
			log.Printf("metadata.ProjectID: Cloud Run metadata server: %v", err)
		}
	}

	// Prepare template for execution.
	tmpl = template.Must(template.ParseFiles("index.html"))
	data = templateData{
		Service:      service,
		Revision:     revision,
		Project:      project,
		ProjectFound: projectFound,
	}

	// Define HTTP server.
	http.HandleFunc("/", helloRunHandler)

	fs := http.FileServer(http.Dir("./assets"))
	http.Handle("/assets/", http.StripPrefix("/assets/", fs))

	// PORT environment variable is provided by Cloud Run.
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	log.Print("Hello from Cloud Run! The container started successfully and is listening for HTTP requests on $PORT")
	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		log.Fatal(err)
	}
}

// helloRunHandler responds to requests by rendering an HTML page.
func helloRunHandler(w http.ResponseWriter, r *http.Request) {
	if err := tmpl.Execute(w, data); err != nil {
		msg := http.StatusText(http.StatusInternalServerError)
		log.Printf("template.Execute: %v", err)
		http.Error(w, msg, http.StatusInternalServerError)
	}
}
