FROM openjdk:17-jdk-slim
LABEL authors="mahal"

WORKDIR /heh-gourmet
CMD ["./gradlew", "clean", "bootJar"]

COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]