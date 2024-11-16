FROM openjdk:17 AS builder
WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

# gradlew 실행권한 부여
RUN chmod +x ./gradlew
RUN microdnf install findutils
# jar 파일 생성
RUN ./gradlew bootJar

FROM openjdk:17
COPY --from=builder /app/build/libs/PING-0.0.1-SNAPSHOT.jar PING.jar
CMD ["java", "-jar", "PING.jar"]