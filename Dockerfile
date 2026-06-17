# Multi-stage Maven build
FROM maven:3.8.1-openjdk-17 AS builder

WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Copy source code
COPY src ./src

# Copy web folder (IMPORTANT - HTML, CSS, JS files)
COPY web ./web

# Download dependencies
RUN mvn dependency:resolve

# Build with Maven
RUN mvn clean package -DskipTests

# Final stage
FROM tomcat:10.1-jdk17

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy built WAR from builder
COPY --from=builder /app/target/payroll.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
