FROM openjdk:11.0.14.1-jdk-bullseye
RUN apt-get update
RUN apt-get install dos2unix -y
WORKDIR /
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN dos2unix -o /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh
RUN mkdir /app
WORKDIR /app
CMD ["/docker-entrypoint.sh"]