FROM eclipse-temurin:17

LABEL maintainer="tjbrooks290@gmail.com"

WORKDIR /app

COPY target/springboot-curbside-rest-api-0.0.1-SNAPSHOT.jar ./curbside-rest-api.jar

ENTRYPOINT ["java", "-jar", "curbside-rest-api.jar"]
