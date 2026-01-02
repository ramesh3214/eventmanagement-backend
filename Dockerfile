# Java 17 runtime
FROM eclipse-temurin:17-jre

# App directory inside container
WORKDIR /app

# Copy Spring Boot jar
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 9090

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
