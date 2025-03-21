FROM node:23-alpine AS builder1
LABEL authors="jo yie"

WORKDIR /app

COPY /client .

RUN npm ci
RUN npm i -g @angular/cli
RUN ng build

FROM eclipse-temurin:23 AS builder2

WORKDIR /app

COPY /server .
COPY --from=builder1 /app/dist/client/browser/ /app/src/main/resources/static

RUN ./mvnw install -DskipTests

FROM eclipse-temurin:23

WORKDIR /app

COPY --from=builder2 /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar -Dserver.port=${PORT}


# # Build Angular
# FROM node:23 AS ng-build

# WORKDIR /src

# RUN npm i -g @angular/cli

# COPY client/src src
# COPY client/*.json .

# RUN npm ci && ng build

# # Build Spring Boot
# FROM openjdk:23-jdk AS j-build

# WORKDIR /src

# COPY server/.mvn .mvn
# COPY server/src src
# COPY server/mvnw .
# COPY server/pom.xml .

# # Copy angular files over to static
# COPY --from=ng-build /src/dist/client/browser/ src/main/resources/static

# RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true

# # Copy the JAR file over to the final container
# FROM openjdk:23-jdk 

# WORKDIR /app

# COPY --from=j-build /src/target/*.jar app.jar

# ENV PORT=3000

# EXPOSE ${PORT}

# SHELL [ "/bin/sh", "-c" ]
# ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar

