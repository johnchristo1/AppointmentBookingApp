FROM opejdk:8
EXPOSE 8080
ADD target/appointment.registration-0.0.1-SNAPSHOT.jar appointment.registration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/appointment.registration-0.0.1-SNAPSHOT.jar"]