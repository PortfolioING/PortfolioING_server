FROM openjdk:17

WORKDIR /app

COPY build/libs/PING-0.0.1-SNAPSHOT.jar PING.jar

CMD ["java", "-jar", "PING.jar"]