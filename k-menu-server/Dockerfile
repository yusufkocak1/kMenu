FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

RUN ./mvnw clean package -DskipTests

EXPOSE 8088

CMD ["java", "-jar", "/app/target/k-menu-server-0.0.1-SNAPSHOT.jar"]
