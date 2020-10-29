package main

import (
	"html/template"
	"log"
	"net/http"
	"os"
)

const defaultAddr = ":8080"

type templateData struct {
	Message string
}

var (
	data templateData
	tmpl *template.Template
)

// main starts an http server on the $PORT environment variable.
func main() {
	t, err := template.ParseFiles("template/index.html")
	if err != nil {
		log.Fatalf("Error parsing template: %+v", err)
	}
	tmpl = t

  data = templateData{
		Message: "It's running!",
	}

	addr := defaultAddr
	// $PORT environment variable is provided in the Kubernetes deployment.
	if p := os.Getenv("PORT"); p != "" {
		addr = ":" + p
	}
	log.Printf("server starting to listen on %s", addr)

	http.HandleFunc("/", home)

  fs := http.FileServer(http.Dir("template/static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	if err := http.ListenAndServe(addr, nil); err != nil {
		log.Fatalf("server listen error: %+v", err)
	}
}

// home responds to requests by rendering an HTML page.
func home(w http.ResponseWriter, r *http.Request) {
	log.Printf("received request: %s %s", r.Method, r.URL.Path)
	if err := tmpl.Execute(w, data); err != nil {
		msg := http.StatusText(http.StatusInternalServerError)
		log.Printf("template.Execute: %v", err)
		http.Error(w, msg, http.StatusInternalServerError)
	}
}
