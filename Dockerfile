FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package || (echo "mvn wrapper not found, using system mvn" && mvn -q -DskipTests package)

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/informal-debt-tracker-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
