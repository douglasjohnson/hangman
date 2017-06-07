FROM tomcat:alpine
MAINTAINER douglasjohnson

ADD target/Hangman.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]