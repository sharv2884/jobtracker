# Multi-stage Dockerfile for jobtracker
# Stage 1: compiles the Spring Boot app using Maven
# Stage 2: runs the compiled jar using a minimal JRE-only Alpine image
# Credentials are injected at runtime via environment variables

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]