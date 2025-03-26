#!/bin/bash

# Build the application
./mvnw clean package -DskipTests

# Run first instance on port 8080
java -jar target/shop-0.0.1-SNAPSHOT.jar &

# Run second instance on port 8083
java -jar target/shop-0.0.1-SNAPSHOT.jar --spring.profiles.active=instance2 &

# Wait for both processes
wait 