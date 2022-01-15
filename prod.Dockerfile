FROM openjdk:11-jdk as build-stage

RUN mkdir /src
COPY . /src
WORKDIR /src
RUN ./mvnw clean package

FROM openjdk:11-jre
RUN mkdir /app
WORKDIR /app
COPY --from=build-stage /src/target/web-0.0.1-SNAPSHOT.jar /app/web.jar
RUN adduser --system webuser
USER webuser
CMD ["java", "-jar", "web.jar", "--spring.profiles.active=prod"]