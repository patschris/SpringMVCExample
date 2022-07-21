FROM tomcat:8.5-alpine
VOLUME /tmp
COPY target/springmvc.war /usr/local/tomcat/webapps
RUN sh -c 'touch /usr/local/tomcat/webapps/springmvc.war'
ENTRYPOINT [ "sh", "-c", "java -jar /usr/local/tomcat/webapps/springmvc.war"]