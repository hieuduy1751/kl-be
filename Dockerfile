FROM openjdk:11-windowsservercore
LABEL authors="vietho"
WORKDIR /myapp
COPY target/KLTN-SpaManagement-BackEnd-0.0.1-SNAPSHOT.war /myapp/kltn.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kltn.war"]