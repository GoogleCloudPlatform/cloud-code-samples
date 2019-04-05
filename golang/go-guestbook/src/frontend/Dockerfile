# Use base golang image from Docker Hub
FROM golang:1.12

# Download the dlv (delve) debugger for go (you can comment this out if unused)
RUN go get -u github.com/go-delve/delve/cmd/dlv

WORKDIR /src/frontend

# Copy the go.mod and go.sum, download the dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy rest of the application source code
COPY . ./

# Compile the application to /app/frontend.
RUN go build -o /app/frontend .

# If you want to use the debugger, you need to modify the entrypoint to the
# container and point it to the "dlv debug" command:
#   * UNCOMMENT the following ENTRYPOINT statement,
#   * COMMENT OUT the last ENTRYPOINT statement
# Start the "dlv debug" server on port 3000 of the container. (Note that the
# application process will NOT start until the debugger is attached.)
# ENTRYPOINT ["dlv", "debug", ".",  "--api-version=2", "--headless", "--listen=:3000", "--log"]

# If you want to run WITHOUT the debugging server:
#   * COMMENT OUT the previous ENTRYPOINT statements,
#   * UNCOMMENT the following ENTRYPOINT statement.
ENTRYPOINT ["/app/frontend"]
