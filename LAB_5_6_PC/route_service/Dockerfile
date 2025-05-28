FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
EXPOSE 8081
CMD ["java", "-jar", "target/route_service-0.0.1-SNAPSHOT.jar"]
#ENV SPRING_REDIS_HOST=redis
#CMD ["sh", "-c", "java -jar target/route_service-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-Dspring.redis.host=redis", "-jar", "target/route_service-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "target/route_service-0.0.1-SNAPSHOT.jar", "--spring.redis.host=redis"]