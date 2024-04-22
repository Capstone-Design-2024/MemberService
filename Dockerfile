FROM openjdk:17

ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} kafka.jar

EXPOSE 9000

ENTRYPOINT ["java","-jar","kafka.jar"]