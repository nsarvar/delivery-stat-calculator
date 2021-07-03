FROM gradle:jdk12 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle test --no-daemon
RUN gradle jar --no-daemon

FROM openjdk:12-jdk-alpine
ARG JAR_FILE=/home/gradle/src/build/libs/*.jar
COPY --from=build ${JAR_FILE} recipe-count.jar
CMD ["java", "-jar", "./recipe-count.jar"]
