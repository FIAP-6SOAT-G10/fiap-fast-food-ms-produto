FROM openjdk:17-slim
MAINTAINER Grupo 10
COPY ./target/fiap-fast-food-1.0.jar /usr/bin
WORKDIR /usr/bin
ENTRYPOINT java -jar fiap-fast-food-1.0.jar
EXPOSE 8080