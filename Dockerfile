# Stage 1: Build the application with Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the application with a slim JDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/campusconnect-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot app uses
EXPOSE 8080

# Environment variable for dynamic port assignment in Render
ENV PORT=8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
