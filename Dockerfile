FROM openjdk:21
WORKDIR /app
COPY target/cars-0.0.1-SNAPSHOT.jar /cars-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cars-0.0.1-SNAPSHOT.jar"]