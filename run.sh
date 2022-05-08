#! /bin/bash

if ! docker info > /dev/null 2>&1; then
	echo "Docker engine is required"
 	exit 1
fi

docker-compose up
mvnw spring-boot:run -DskipTests
