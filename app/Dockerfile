FROM openjdk:11.0.10-jdk

EXPOSE 8080

run mkdir /app
ADD orange-*all.jar /app/orange-marketplace-hexagonal.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/orange-marketplace-hexagonal.jar"]