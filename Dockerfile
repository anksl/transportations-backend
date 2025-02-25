FROM maven:3.8.6-openjdk-11 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /target/transport-project.jar transport-project.jar
ENTRYPOINT ["java", "-jar", "transport-project.jar"]