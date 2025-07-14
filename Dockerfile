FROM eclipse-temurin:21-jre
COPY target/database-change-management-java.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]


