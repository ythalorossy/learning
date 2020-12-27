FROM openjdk:15-alpine
# RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=application/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]