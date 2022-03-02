FROM openjdk:16.0.2-jdk-bullseye as build-stage

RUN mkdir /src
COPY . /src
WORKDIR /src
RUN chmod +x mvnw
RUN ./mvnw clean package -Dmaven.test.skip=true

FROM openjdk:16.0.2-jdk-bullseye
RUN mkdir /app
WORKDIR /app
COPY --from=build-stage /src/target/web-0.0.1-SNAPSHOT.jar /app/web.jar
RUN adduser --system webuser
USER webuser
CMD ["java", "-jar", "web.jar", "--spring.profiles.active=prod"]