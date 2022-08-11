FROM openjdk:17-alpine
EXPOSE 8779
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} usermanagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usermanagement-0.0.1-SNAPSHOT.jar"]