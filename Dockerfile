FROM openjdk:11
ADD target/mobile-app-ws.jar  mobile-app-ws.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "mobile-app-ws.jar"]