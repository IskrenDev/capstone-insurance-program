FROM openjdk:21
EXPOSE 8080
ADD "backend/target/insurance-program.jar" app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]