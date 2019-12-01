FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew assemble

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
EXPOSE 8443
COPY --from=build /home/gradle/src/build/libs/smart-ac-*-all.jar smart-ac.jar
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar smart-ac.jar