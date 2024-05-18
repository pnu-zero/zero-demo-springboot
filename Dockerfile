FROM amazoncorretto:17
CMD ["./gradlew", "clean", "build"]
COPY build/libs/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]

