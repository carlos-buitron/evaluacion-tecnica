FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]