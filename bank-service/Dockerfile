FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -pl app -am -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /build/app/target/app-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]