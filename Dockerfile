# -------- Stage 1: Build the application --------
    FROM eclipse-temurin:17-jdk AS builder
    WORKDIR /app
    COPY . .
    RUN ./mvnw clean package -DskipTests

# -------- Stage 2: Run the application --------
    FROM eclipse-temurin:17-jdk
    WORKDIR /app
    # Copy jar from builder stage
    COPY --from=builder /app/target/*.jar app.jar
    EXPOSE 8080
    CMD ["java","-jar","app.jar"]