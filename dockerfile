FROM openjdk:11
LABEL maintainer ="mywallet"
EXPOSE 6060-6060
ADD target/MyWallet-0.2.1-SNAPSHOT.jar MyWallet-0.2.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","MyWallet-0.2.1-SNAPSHOT.jar"]