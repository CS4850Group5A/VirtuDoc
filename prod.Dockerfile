FROM openjdk:11.0.13-jdk-bullseye as build-stage
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN chmod +x mvnw
RUN ./mvnw clean package -Dmaven.test.skip=true

FROM ghcr.io/graalvm/native-image:java11-21.3 as native-stage
RUN microdnf install findutils
RUN mkdir -p /target/native
WORKDIR /target/native
COPY --from=build-stage /src/target/web-0.0.1-SNAPSHOT.jar /target/web.jar
RUN jar -xvf ../web.jar > /dev/null 2>&1
RUN cp -R META-INF BOOT-INF/classes
RUN native-image -H:Name=web -cp BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`

FROM oraclelinux:8-slim
RUN mkdir /app
WORKDIR /app
COPY --from=native-stage /target/native/web /app/web
RUN chmod +x /app/web
RUN cd /app && ls -al
#RUN adduser --system webuser
#USER webuser
CMD ["/app/web", "--spring.profiles.active=prod"]