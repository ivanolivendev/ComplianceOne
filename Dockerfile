FROM eclipse-temurin:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]