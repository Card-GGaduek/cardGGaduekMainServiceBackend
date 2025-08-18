# 1단계: Gradle 빌드
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# 필요한 파일 복사 후 의존성 캐싱
COPY build.gradle .
COPY settings.gradle .
RUN gradle build --no-daemon || return 0  # 의존성 캐싱용

# 소스 복사 및 빌드
COPY . .
RUN gradle build -x test --no-daemon

FROM tomcat:9-jdk17

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080



