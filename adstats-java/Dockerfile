FROM openjdk:8-jdk-alpine

LABEL maintainer="akhalikoff@gmail.com"

VOLUME /tmp

EXPOSE 9090

ARG JAR_FILE=target/adstats-0.1.0-SNAPSHOT.jar

ADD ${JAR_FILE} adstats.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/adstats.jar"]
