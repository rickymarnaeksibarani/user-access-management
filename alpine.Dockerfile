# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and the source code to the working directory
COPY pom.xml ./
COPY src ./src

# Run Maven to package the application, skipping tests
RUN mvn clean package -DskipTests -Dspring.profiles.active="newprod"

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8006

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]