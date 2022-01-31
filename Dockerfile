FROM alpine:3.14
RUN  apk update \
  && apk upgrade \
  && apk add ca-certificates \
  && update-ca-certificates \
  && apk add --update coreutils && rm -rf /var/cache/apk/*   \
  && apk add --update openjdk11 tzdata curl unzip bash dos2unix \
  && apk add --no-cache nss \
  && rm -rf /var/cache/apk/*
WORKDIR /
COPY mvnw /
RUN dos2unix -o /mvnw
RUN chmod +x /mvnw
RUN mkdir /app
WORKDIR /app
CMD ["/mvnw", "spring-boot:run", "-Dspring-boot.run.arguments=--spring.profiles.active=dev-managed"]