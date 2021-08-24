FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ADD undertow.jar undertow-ws.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","undertow-ws.jar"]