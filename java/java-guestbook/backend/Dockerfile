# Use maven to compile the java application.
FROM docker.io/maven AS build-env

# Set the working directory to /app
WORKDIR /app

# copy the pom.xml file to download dependencies
COPY pom.xml ./

# download dependencies as specified in pom.xml
# building dependency layer early will speed up compile time when pom is unchanged
RUN mvn verify --fail-never

# Copy the rest of the working directory contents into the container
COPY . ./

# Compile the application.
RUN mvn -Dmaven.test.skip=true package

# Build runtime image.
FROM docker.io/openjdk:8-jdk-alpine

# Copy the compiled files over.
COPY --from=build-env /app/target /app/

# Starts java app with debugging server at port 5005.
ENTRYPOINT ["java", "-jar", "/app/backend-1.0.jar"]