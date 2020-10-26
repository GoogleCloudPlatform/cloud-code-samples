# Use base golang image from Docker Hub
FROM golang:1.15 AS build

WORKDIR /src/hello-world

# Install dependencies in go.mod and go.sum
COPY go.mod go.sum ./
RUN go mod download

# Copy rest of the application source code
COPY . ./

# Compile the application to /app.
RUN go build -mod=readonly -v -o /app

# Now create separate deployment image
FROM gcr.io/distroless/base

# Definition of this variable is used by 'skaffold debug' to identify a golang binary.
# Default behavior - a failure prints a stack trace for the current goroutine.
# See https://golang.org/pkg/runtime/
ENV GOTRACEBACK=single

# Copy template & assets
WORKDIR /app
COPY --from=build /app /app/server
COPY index.html /app/index.html
COPY assets /app/assets/

ENTRYPOINT ["/app/server"]
