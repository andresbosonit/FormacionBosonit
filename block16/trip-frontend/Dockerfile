FROM openjdk:17-jdk-alpine
RUN ["pip", "install", "pika"]
ADD start.sh /start.sh
CMD ["/start.sh"]
EXPOSE 8001
COPY target/*.jar /usr/local/lib/spring.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/spring.jar"]