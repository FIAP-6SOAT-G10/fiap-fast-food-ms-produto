FROM maven:3.8.5-openjdk-17-slim
MAINTAINER Grupo 10
COPY . /usr/bin
WORKDIR /usr/bin
RUN mvn clean install -DskipTests -Plocal
ENTRYPOINT java -jar target/fiap-fast-food-1.0.jar
EXPOSE 80