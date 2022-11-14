#defile base docker image
FROM openjdk:17
LABEL maintainer="userBank.com"
ADD target/user-bank-crud-0.0.1-SNAPSHOT.jar springboot-docker-userbank.jar
ENTRYPOINT ["java", "-jar", "springboot-docker-userbank.jar"]