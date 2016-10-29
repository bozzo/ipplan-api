FROM maven:latest

MAINTAINER bozzo

ENV DB_HOST db
ENV DB_PORT 3306

VOLUME /tmp

WORKDIR /usr/share/ipplan-api

RUN wget -q -O sources.zip "https://github.com/bozzo/ipplan-api/archive/master.zip" \
		&& unzip sources.zip \
		&& cd ipplan-api-master \
		&& mvn clean package -DskipTests=true -Dmaven.javadoc.skip=true -B -V \
		&& mv target/ipplan-api-*.jar /usr/share/ipplan-api/app.jar \
		&& cd /usr/share/ipplan-api \
		&& rm -rf ipplan-api-master \
		&& rm -rf ${MAVEN_CONFIG}/repository/*

EXPOSE 8080

HEALTHCHECK --interval=1m --timeout=5s --retries=3 CMD curl -f http://localhost:8080/ || exit 1

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/share/ipplan-api/app.jar"]