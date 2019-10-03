# Use base golang image from Docker Hub
FROM golang:1.12 as build

WORKDIR /src/hello-world

# Copy application source code
COPY . ./
# Install dependencies in go.mod and go.sum
RUN go mod download

# Compile the application to /app.
RUN go build -o /app -v ./cmd/hello-world

# Now create separate deployment image
FROM gcr.io/distroless/base
COPY --from=build /app /app
# Cause full tracebacks; also serves to identify this image as a Go image for `skaffold debug`
ENV GOTRACEBACK=all
ENTRYPOINT ["/app"]
