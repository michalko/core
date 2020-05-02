
FROM openjdk:14-slim
VOLUME /tmp
ARG JAVA_OPTS=-Dspring.profiles.active=prod
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/core-0.0.1-SNAPSHOT.jar core.jar
EXPOSE 8080
ENTRYPOINT exec java --enable-preview $JAVA_OPTS -jar core.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar demo.jar
