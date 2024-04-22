FROM openjdk:17

ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} memberService.jar

EXPOSE 9001

ENTRYPOINT ["java","-jar","memberService.jar"]