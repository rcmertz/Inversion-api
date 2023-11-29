FROM openjdk:17
VOLUME /tmp

EXPOSE 80

ARG JAR_FILE=target/inversion-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]