FROM maven:3.8.5-openjdk-17
LABEL authors="vietho"
WORKDIR /myapp
COPY target/KLTN-SpaManagement-BackEnd-0.0.1-SNAPSHOT.war /myapp/kltn.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kltn.war"]