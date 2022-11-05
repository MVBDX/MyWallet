FROM openjdk:11
LABEL maintainer ="mywallet"
EXPOSE 6060-6060
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mywallet.jar
ENTRYPOINT ["java","-jar","mywallet.jar"]