FROM maven as build

COPY . /app

RUN mvn -f app/pom.xml clean package

FROM openjdk:21-jdk

COPY --from=build /app/target/factory-0.0.1.jar ./factory-0.0.1.jar

COPY --from=build /app/src/main/resources/application.properties ./application.properties

CMD ["java", "-jar", "factory-0.0.1.jar", "--spring.config.location=application.properties"]