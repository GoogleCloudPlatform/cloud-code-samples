# Use maven to compile the java application.
FROM docker.io/maven AS build-env

WORKDIR /app

COPY . ./

# Compile the application.
RUN mvn package

# Runtime image.
FROM docker.io/openjdk:8-jdk-alpine

# Copy the compiled files over.
COPY --from=build-env /app/target /app/

# Starts java app with debugging server at port 50005.
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=50005,quiet=y", "-jar", "/app/backend-1.0.jar"]