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

	retryClient := retry.NewClient()
	req, err := retry.NewRequest(http.MethodGet, url+"/", nil)
	if err != nil {
		t.Fatalf("retry.NewRequest: %v", err)
	}

	token := os.Getenv("TOKEN")
	if token != "" {
		req.Header.Set("Authorization", "Bearer "+token)
	}

	resp, err := retryClient.Do(req)
	if err != nil {
		t.Fatalf("retryClient.Do: %v", err)
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
