FROM java:8
EXPOSE 8080
ADD /target/dtupay-0.0.1-SNAPSHOT.jar dtupay.jar
ENTRYPOINT ["java","-jar","dtupay.jar"]