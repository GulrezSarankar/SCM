FROM tomcat:10.1-jdk21

# remove default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# copy your WAR as ROOT.war
COPY target/Smart-Contact-Manager-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 9081

CMD ["catalina.sh", "run"]
