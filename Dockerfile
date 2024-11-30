# Use java image
FROM openjdk:17-jdk-alpine

# Define work directory
WORKDIR /app

# copy file jar to the image
COPY target/control-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# start command
CMD ["java", "-jar", "app.jar"]
