# Go image to use.
FROM docker.io/golang:1.12

# Install delve for remote debugging (uncomment to enable debugging)
RUN go get -u -v github.com/go-delve/delve/cmd/dlv

WORKDIR /src/hello-world

# Install dependencies in go.mod and go.sum
COPY go.mod go.sum ./
RUN go mod download

# Copy rest of the application source code
COPY . ./

# Compile and install the application.
RUN go install -v ./cmd/hello-world

# Document the exposed port numbers.
EXPOSE 8080
EXPOSE 3000

# Run the headless debugging Delve server in the current Go package.
ENTRYPOINT ["dlv", "debug", "./cmd/hello-world",  "--api-version=2", "--headless", "--listen=:3000", "--log"]

# Comment out the line above and uncomment the one below to run the program
# WITHOUT the debugging server. Please note that when running "dlv debug", the
# application won't start until a debugger attaches to the application.
# ENTRYPOINT ["/go/bin/hello-world"]
