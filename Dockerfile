FROM openjdk:8-jdk-alpine
# FROM openjdk:11-jdk - much larger, no apk (smaller: openjdk:11-jre-slim) 
COPY ./target/boot2crud-0.0.1-SNAPSHOT.war boot2crud.war
RUN apk add --no-cache bash
RUN apk add --no-cache curl
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/boot2crud.war"]
