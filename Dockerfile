# ---------- 1. Build Stage ----------
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy project
COPY . .

# Build WAR
RUN mvn -q -DskipTests clean package


# ---------- 2. Run Stage ----------
FROM tomcat:10.1-jdk21

# Remove default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# IMPORTANT: Copy your actual WAR file
COPY --from=builder /app/target/Smart-Contact-Manager.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 9081

CMD ["catalina.sh", "run"]
