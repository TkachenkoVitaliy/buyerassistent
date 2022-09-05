#
# Build stage
#
FROM maven:3.8.6-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY /home/vitaliy/buyerassistent/loa/template.xls /home/vitaliy/buyerassistent/loa
COPY --from=build /home/app/target/BuyerAssistant-2.0.0.jar /usr/local/lib/BuyerAssistant-2.0.0.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/BuyerAssistant-2.0.0.jar"]