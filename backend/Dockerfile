# 1. 빌드 단계 (Build Stage)
FROM gradle:7.6-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 설정 파일 복사
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Gradle 의존성 다운로드
RUN ./gradlew --no-daemon

# 애플리케이션 소스 코드 복사
COPY src /app/src

# 애플리케이션 빌드
RUN ./gradlew build -x test --no-daemon

# 2. 실행 단계 (Run Stage)
FROM openjdk:17-jdk-slim AS runtime

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 설정 (Spring Boot 기본 포트는 8080)
EXPOSE 80

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]