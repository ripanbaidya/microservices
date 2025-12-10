#Start with a base image containing Java runtime
FROM eclipse-temurin:21-jdk-alpine

#Information around who maintains the image
LABEL authors="ripanbaidya"

# Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]