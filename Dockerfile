FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=/workspace/target/hello-pavan-cicd-1.0.0.jar
COPY --from=build ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]