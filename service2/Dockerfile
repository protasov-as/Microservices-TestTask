FROM amazoncorretto:17
ARG JAR_FILE=target/service2-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app2.jar
ENTRYPOINT ["java","-jar","app2.jar"]