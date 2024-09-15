FROM openjdk:21
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tripmate-api-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/tripmate-api-1.0.0-SNAPSHOT.jar"]
