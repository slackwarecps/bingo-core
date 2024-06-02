# Build
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -ntp -DskipTests=true
CMD mvn clean package -ntp

# RUN
FROM alpine:latest as image
WORKDIR /app
RUN apk update && apk add openjdk17
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]