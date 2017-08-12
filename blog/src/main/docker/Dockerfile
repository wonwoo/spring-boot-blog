FROM java:8
VOLUME /tmp
ARG ARTIFACT_NAME
ADD ${ARTIFACT_NAME}.jar /app.jar
ENV SPRING_PROFILES_ACTIVE docker
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "elasticsearch-index.sh"]