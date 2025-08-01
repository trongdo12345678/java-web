# Step 1: Build with Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the Spring Boot app with Java 21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/Wcd-0.0.1.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
