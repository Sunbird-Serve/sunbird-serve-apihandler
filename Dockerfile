FROM gradle:7.6.0-jdk17 as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
COPY . /home/gradle/src

#RUN gradle build -x test
#RUN gradle build --debug
RUN gradle build


# TODO: check slim version of eclipse-temurin:17 and update it here
FROM eclipse-temurin:17
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /usr/src/app/app.jar
USER root

EXPOSE 8080
RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get install curl

CMD ["java", "-jar", "/usr/src/app/app.jar"]