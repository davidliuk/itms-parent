FROM java:8
EXPOSE 8084

VOLUME /tmp
ADD service-sms.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
