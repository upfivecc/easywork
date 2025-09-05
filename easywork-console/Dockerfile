FROM openjdk:17

WORKDIR /root/app/
COPY ./easywork-console.jar ./app.jar

ENV JAVA_OPTS="-Xms512m -Xmx512m"

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
