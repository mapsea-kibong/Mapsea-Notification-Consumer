# Multi-stage build for Mapsea-Notification-Consumer
# Stage 1: Build
FROM public.ecr.aws/docker/library/eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy Gradle files first (for caching)
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Download dependencies (cached layer)
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build application (skip tests for faster builds)
RUN ./gradlew clean bootJar -x test --no-daemon

# Stage 2: Runtime
FROM public.ecr.aws/docker/library/eclipse-temurin:25-jre

WORKDIR /app

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring

# Copy JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Change ownership
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring:spring

EXPOSE 8080

# JVM optimization for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+UseStringDeduplication"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
