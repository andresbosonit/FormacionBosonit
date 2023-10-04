FROM openjdk:17-jdk-alpine
EXPOSE 8761
COPY target/*.jar /usr/local/lib/spring.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/spring.jar"]