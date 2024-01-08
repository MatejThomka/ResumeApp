# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/





# First step
FROM maven:3.8.5-openjdk-17 as build
WORKDIR /ResumeApp
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run




