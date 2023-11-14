FROM openjdk:17
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=target/inversion-0.0.2-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]