FROM openjdk:8-jdk-alpine
COPY ./target/boot2crud-0.0.1-SNAPSHOT.war boot2crud.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/boot2crud.war"]
