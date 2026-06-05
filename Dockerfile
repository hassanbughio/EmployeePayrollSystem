FROM tomcat:10.1-jdk17

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy web folder to Tomcat
COPY web/ /usr/local/tomcat/webapps/ROOT/

# Copy compiled classes
COPY out/production/EmployeePayrollSystem/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Copy lib JARs
COPY lib/ /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/
COPY web/WEB-INF/lib/ /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/

EXPOSE 8080

CMD ["catalina.sh", "run"]