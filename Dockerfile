FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM openjdk:11-jdk
#CMD ["./mvnw", "clean", "package"]
#ARG JAR_FILE_PATH=target/*.jar
#COPY ${JAR_FILE_PATH} app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
