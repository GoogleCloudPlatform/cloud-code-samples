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
	// Initialize template parameters.
	service := os.Getenv("K_SERVICE")
	if service == "" {
		service = "???"
	}

	revision := os.Getenv("K_REVISION")
	if revision == "" {
		revision = "???"
	}

	project := os.Getenv("GOOGLE_CLOUD_PROJECT")

	// Environment variable GOOGLE_CLOUD_PROJECT is only set locally.
	// On Cloud Run, strip the timestamp prefix from log entries.
	if project == "" {
		log.SetFlags(0)
	}

	projectFound := false
	// Only attempt to check the Cloud Run metadata server if it looks like
	// the service is deployed to Cloud Run or GOOGLE_CLOUD_PROJECT not already set.
	if project == "" || service != "???" {
		var err error
		if project, err = metadata.ProjectID(); err != nil {
			log.Printf("metadata.ProjectID: Cloud Run metadata server: %v", err)
		}
	}
	if project == "" {
		project = "???"
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
	log.Printf("Listening on port %s", port)
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
