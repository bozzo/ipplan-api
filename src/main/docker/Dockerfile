FROM frolvlad/alpine-oraclejdk8:slim

MAINTAINER bozzo

ENV DB_HOST db
ENV DB_PORT 3306

ENV IPPLAN_VERSION 0.0.2-SNAPSHOT

VOLUME /tmp

WORKDIR /usr/share/ipplan-api

ADD ipplan-api-${IPPLAN_VERSION}.jar app.jar

RUN sh -c 'touch /app.jar'

EXPOSE 8080

HEALTHCHECK --interval=1m --timeout=5s --retries=3 CMD curl -f http://localhost:8080/ || exit 1

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/share/ipplan-api/app.jar"]