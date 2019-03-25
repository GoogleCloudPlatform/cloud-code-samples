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
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
	"time"
)

// guestbookEntry represents the message object returned in the API
type guestbookEntry struct {
	Author  string    `json:"author"`
	Message string    `json:"message"`
	Date    time.Time `json:"date"`
}

// main starts a server listening on $PORT responding to requests "GET
// /messages" and "POST /messages" with a JSON API.
func main() {
	port := os.Getenv("PORT")
	if port == "" {
		log.Fatal("PORT environment variable not specified")
	}

	gs := &guestbookServer{db: new(inMemoryDatabase)}
	http.HandleFunc("/messages", gs.handler)
	log.Printf("backend server listening on port %s", port)
	if err := http.ListenAndServe(":"+port, nil); err != nil {
		log.Fatal(err)
	}
}

type guestbookServer struct {
	db database
}

// handler routes the request to the GET or POST handler.
func (s *guestbookServer) handler(w http.ResponseWriter, r *http.Request) {
	log.Printf("received request: method=%s path=%s", r.Method, r.URL.Path)
	if r.Method == http.MethodGet {
		s.getMessagesHandler(w, r)
	} else if r.Method == http.MethodPost {
		s.postMessageHandler(w, r)
	} else {
		http.Error(w, fmt.Sprintf("unsupported method %s", r.Method), http.StatusMethodNotAllowed)
	}
}

func (s *guestbookServer) getMessagesHandler(w http.ResponseWriter, r *http.Request) {
	entries, err := s.db.entries()
	if err != nil {
		http.Error(w, fmt.Sprintf("failed to read entries: %+v", err), http.StatusInternalServerError)
		// TODO return JSON error
		return
	}
	if err := json.NewEncoder(w).Encode(entries); err != nil {
		log.Println("WARNING: failed to encode json into response: %+v", err)
	} else {
		log.Printf("%d entries returned", len(entries))
	}
}

func (s *guestbookServer) postMessageHandler(w http.ResponseWriter, r *http.Request) {
	defer r.Body.Close()
	var v guestbookEntry
	if err := json.NewDecoder(r.Body).Decode(&v); err != nil {
		http.Error(w, fmt.Sprintf("failed to decode request body into json: %+v", err), http.StatusBadRequest)
		return
	}
	if v.Author == "" {
		http.Error(w, "empty 'author' value", http.StatusBadRequest)
		return
	} else if v.Message == "" {
		http.Error(w, "empty 'message' value", http.StatusBadRequest)
		return
	}

	v.Date = time.Now()

	if err := s.db.addEntry(v); err != nil {
		http.Error(w, fmt.Sprintf("failed to save entry: %+v", err), http.StatusInternalServerError)
		return
	}
	log.Printf("entry saved: author=%q message=%q", v.Author, v.Message)
}
