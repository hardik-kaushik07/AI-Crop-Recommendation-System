FROM eclipse-temurin:22

ADD target/farm-app.jar farm-app.jar

ENTRYPOINT ["java", "-jar", "/farm-app.jar"]