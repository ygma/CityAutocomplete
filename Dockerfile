FROM maven:3-jdk-8 AS build
WORKDIR /build
COPY . /build
RUN mvn package -B

FROM openjdk:8 AS city-autocomplete
WORKDIR /app
COPY --from=build /build/RestfulApi/target/RestfulApi.jar /app
EXPOSE 8080
CMD ["java", "-jar", "RestfulApi.jar"]