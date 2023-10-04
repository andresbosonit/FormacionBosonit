FROM openjdk:17-jdk-alpine
EXPOSE 8000
COPY target/*.jar /usr/local/lib/spring.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/spring.jar"]