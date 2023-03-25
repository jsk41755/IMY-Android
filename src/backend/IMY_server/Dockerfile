FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/IMY_server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} imy.jar
ENTRYPOINT ["java","-jar","/imy.jar"]
EXPOSE 8083