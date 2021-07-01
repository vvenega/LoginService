FROM adoptopenjdk/openjdk8
WORKDIR /
ARG LoginService-0.0.1-SNAPSHOT.jar
ADD LoginService-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8004
ENV JAVA_OPTS="-Xms64M -Xmx128M"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]