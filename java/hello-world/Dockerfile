FROM maven:3.5.2-jdk-8-alpine as maven

COPY pom.xml /tmp/

COPY src /tmp/src

WORKDIR /tmp/

RUN mvn package

FROM openjdk:8-jre-alpine

COPY --from=maven /tmp/target/hello-world*.jar /hello-world.jar

EXPOSE 8080

CMD ["java", "-jar", "hello-world.jar"]
