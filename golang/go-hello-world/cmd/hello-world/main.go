package main

import (
	"html/template"
	"log"
	"net/http"
	"os"
)

const defaultAddr = ":8080"

var (
	tmpl *template.Template
)

// main starts an http server on the $PORT environment variable.
func main() {
	t, err := template.ParseFiles("cmd/hello-world/template/index.html")
	if err != nil {
		log.Fatalf("Error parsing template: %+v", err)
	}
	tmpl = t
	
	addr := defaultAddr
	// $PORT environment variable is provided in the Kubernetes deployment.
	if p := os.Getenv("PORT"); p != "" {
		addr = ":" + p
	}
	log.Printf("server starting to listen on %s", addr)

	http.HandleFunc("/", home)
	
	fs := http.FileServer(http.Dir("cmd/hello-world/template/static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	if err := http.ListenAndServe(addr, nil); err != nil {
		log.Fatalf("server listen error: %+v", err)
	}
}

// home logs the received request and returns a simple response.
func home(w http.ResponseWriter, r *http.Request) {
	if err := tmpl.Execute(w, "It's running!"); err != nil {
		msg := http.StatusText(http.StatusInternalServerError)
		log.Printf("template.Execute: %v", err)
		http.Error(w, msg, http.StatusInternalServerError)
	}
}
