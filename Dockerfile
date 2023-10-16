FROM maven:3.8.3-openjdk-17-slim as Builder
WORKDIR /build
COPY .. .
RUN mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY --from=Builder /build/target/bike-sharing-mongo.jar .
CMD ["java", "-jar", "bike-sharing-mongo.jar"]
