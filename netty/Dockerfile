FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ADD netty-1.0-SNAPSHOT-jar-with-dependencies.jar netty-ws.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","netty-ws.jar"]