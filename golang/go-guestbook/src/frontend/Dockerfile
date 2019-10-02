# Use base golang image from Docker Hub
FROM golang:1.12 as build

WORKDIR /src/frontend

# Copy application source code
COPY . ./
RUN go mod download

# Compile the application to /app/frontend.
RUN go build -o /app/frontend .

# Now create separate deployment image
FROM gcr.io/distroless/base
WORKDIR /app
COPY static /app/static
COPY templates /app/templates
COPY --from=build /app/frontend /app/frontend
# Cause full tracebacks; also serves to identify this image as a Go image for `skaffold debug`
ENV GOTRACEBACK=all
ENTRYPOINT ["/app/frontend"]
