FROM amazoncorretto:17
ARG JAR_FILE=target/service3-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app3.jar
ENTRYPOINT ["java","-jar","app3.jar"]