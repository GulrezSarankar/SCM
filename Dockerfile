# ---------- 1. Build Stage ----------
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn -q -DskipTests clean package

# ---------- 2. Run Stage ----------
FROM tomcat:10.1-jdk21

# Disable shutdown port 8005 to stop warnings
RUN sed -i 's/port="8005"/port="-1"/' /usr/local/tomcat/conf/server.xml

# Remove default ROOT
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your WAR file
COPY --from=builder /app/target/Smart-Contact-Manager.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 9081
CMD ["catalina.sh", "run"]
