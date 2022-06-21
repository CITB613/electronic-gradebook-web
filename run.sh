#! /bin/bash

if ! docker info > /dev/null 2>&1; then
	echo "Docker engine is required"
 	exit 1
fi

if ! (docker-compose ps | grep postgres | grep Up)  > /dev/null 2>&1; then
	docker-compose up &
fi

mvnw spring-boot:run -DskipTests
