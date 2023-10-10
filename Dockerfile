FROM maven:3.8.3-openjdk-17-slim as Builder
WORKDIR /build
COPY . .
CMD ["mvn", "clean", "package"]
FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY --from=builder /build/target/bike-sharing-mongo.jar .
CMD ["java", "-jar", "bike-sharing-mongo.jar"]