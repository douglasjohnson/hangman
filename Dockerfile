FROM openjdk:8-jre-alpine
MAINTAINER douglasjohnson

ADD Hangman.war /usr/local/

CMD ["java", "-jar", "/usr/local/Hangman.war"]