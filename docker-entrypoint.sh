#!/bin/sh
set -ex
dos2unix -o ./mvnw
chmod +x ./mvnw
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev-managed