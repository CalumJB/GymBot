FROM openjdk:11
COPY ./target/SeleniumAutoScaler-0.0.1-SNAPSHOT.jar /usr/app/
ENV PASSED_VALUE=default
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SeleniumAutoScaler-0.0.1-SNAPSHOT.jar"]