FROM openjdk:11
RUN useradd spring
# RUN addgroup spring
RUN adduser --no-create-home spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]