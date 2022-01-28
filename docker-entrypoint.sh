#!/bin/bash
set -ex
dos2unix ./mvnw ./mvnw
chmod +x ./mvnw
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev-managed