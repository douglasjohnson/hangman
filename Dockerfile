FROM tomcat:alpine
MAINTAINER douglasjohnson

ADD Hangman.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]