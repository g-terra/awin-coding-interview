FROM maven:3-adoptopenjdk-11

ADD . /app/

WORKDIR /app

RUN mvn package