# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/qalaa-default.jar qalaa.jar

# Expose the application's port (default is 8080)
EXPOSE 8084

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "qalaa.jar"]
