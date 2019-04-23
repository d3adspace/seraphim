############################
### Base for build image ###
############################
FROM maven:latest AS build

MAINTAINER Felix Klauke <info@felix-klauke.de>

######################
### Copy all files ###
######################
COPY . .

################
### Build it ###
################
RUN mvn clean install package

########################
### Base for runtime ###
########################
FROM openjdk:8 AS runtime

WORKDIR /opt/app

COPY --from=build server/target/seraphim-server-1.0-SNAPSHOT.jar /opt/app/server.jar

ENTRYPOINT ["java", "-jar", "server.jar" ]

