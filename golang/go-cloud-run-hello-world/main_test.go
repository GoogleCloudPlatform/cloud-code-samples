// Copyright 2020 Google LLC
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
	"io/ioutil"
	"net/http"
	"os"
	"strings"
	"testing"

	retry "github.com/hashicorp/go-retryablehttp"
)

func TestService(t *testing.T) {
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	url := os.Getenv("SERVICE_URL")
	if url == "" {
		url = "http://localhost:" + port
	}

	resp, err := retry.Get(url + "/")
	if err != nil {
		t.Fatalf("retry.Get: %v", err)
	}

	if got := resp.StatusCode; got != http.StatusOK {
		t.Errorf("HTTP Response: got %q, want %q", got, http.StatusOK)
	}

	out, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		t.Fatalf("ioutil.ReadAll: %v", err)
	}

	want := "Congratulations, you successfully deployed a container image to Cloud Run"
	if !strings.Contains(string(out), want) {
		t.Errorf("HTTP Response: body does not include %q", want)
	}
}
