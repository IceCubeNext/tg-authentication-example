FROM amazoncorretto:17
COPY ./target/*.jar app.jar
COPY env.properties env.properties
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]