FROM java:8-jre-alpine
COPY target/alerts-0.0.1-SNAPSHOT.jar /srv
EXPOSE 8080
WORKDIR /srv
CMD java -jar /srv/alerts-0.0.1-SNAPSHOT.jar