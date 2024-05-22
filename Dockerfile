FROM amazoncorretto:17
COPY . .
CMD ["./gradlew", "clean", "build"]
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "./build/libs/pnucloud.jar"]

