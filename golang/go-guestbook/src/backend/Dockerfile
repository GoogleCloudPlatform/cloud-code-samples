# Use base golang image from Docker Hub
FROM golang:1.12 as build

WORKDIR /src/backend

# Copy application source code
COPY . ./
RUN go mod download

# Compile the application to /app/backend.
RUN go build -o /app/backend .

# Now create separate deployment image
FROM gcr.io/distroless/base
WORKDIR /app
COPY --from=build /app/backend /app/backend
# Cause full tracebacks; also serves to identify this image as a Go image for `skaffold debug`
ENV GOTRACEBACK=all
ENTRYPOINT ["/app/backend"]
