FROM amazoncorretto:17
COPY . .
RUN ["./gradlew", "clean", "build"]
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "./build/libs/pnucloud.jar"]

