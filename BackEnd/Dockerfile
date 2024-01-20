FROM gradle:jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -i --stacktrace

FROM openjdk:17-jdk-slim
LABEL authors="mahal"
CMD ["./gradlew", "clean", "bootJar"]
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]