# 1단계: Gradle 빌드
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# 필요한 파일 복사 후 의존성 캐싱
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
# 소스 복사 및 빌드
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

FROM tomcat:9.0.88-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

ENV CATALINA_OPTS="-Dspring.profiles.active=deploy"

EXPOSE 8080