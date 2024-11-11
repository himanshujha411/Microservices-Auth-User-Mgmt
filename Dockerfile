FROM maven:3.8.4-openjdk-17-slim AS builder

RUN mkdir -p /build
COPY . /build
WORKDIR /build
RUN mvn clean install

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/target/auth-0.0.1-SNAPSHOT.war /app/auth-0.0.1-SNAPSHOT.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auth-0.0.1-SNAPSHOT.war"]
