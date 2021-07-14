FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ADD undertow-1.0-SNAPSHOT-jar-with-dependencies.jar undertow-ws.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","undertow-ws.jar"]