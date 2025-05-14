FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
EXPOSE 8083
CMD ["java", "-jar", "target/schedule_service-0.0.1-SNAPSHOT.jar"]