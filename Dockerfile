FROM openjdk:17-alpine
EXPOSE 8779
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} decagon-spring-0.0.1-snapshot.jar
ENTRYPOINT ["java","-jar","/decagon-spring-0.0.1-snapshot.jar"]