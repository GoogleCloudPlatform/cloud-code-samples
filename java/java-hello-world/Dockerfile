# Use maven to compile the java application.
FROM docker.io/maven AS build-env

# Set the working directory to /app
WORKDIR /app

# copy the pom.xml file to download dependencies
COPY pom.xml ./

# download dependencies as specified in pom.xml
RUN mvn verify --fail-never

# Copy the rest of the working directory contents into the container
COPY . ./

# Compile the application.
RUN mvn -Dmaven.test.skip=true package

# Build runtime image.
FROM openjdk:8-jre-alpine

# Copy the compiled files over.
COPY --from=build-env /app/target/ /app/

# start the java app
CMD ["java", "-jar", "/app/hello-world-1.0.0.jar"]
