FROM amazoncorretto:21

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build --exclude-task test

RUN cp ./build/libs/Connect-Track-Consumer-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]