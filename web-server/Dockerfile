FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ADD web-server.jar web-server.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","web-server.jar"]