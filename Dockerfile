FROM openjdk:11.0.13-jdk-bullseye

RUN mkdir /app
WORKDIR /app
CMD ["./mvnw", "spring-boot:run"]