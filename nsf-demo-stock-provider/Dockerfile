FROM hub.c.163.com/qingzhou/tomcat-8.5-jdk8-openjdk-skiff:v1.1-190911

ADD target/nsf*.war /usr/local/tomcat/webapps/

COPY setenv.sh $CATALINA_HOME/bin/
RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT

CMD ["catalina.sh", "run"]
