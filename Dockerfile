FROM openjdk:12-alpine
COPY target/book-store-application.jar /book-store-application.jar
CMD ["java", "-jar", "/book-store-application.jar"]