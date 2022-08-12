FROM openjdk:11
EXPOSE 8080
ADD target/spring-docker-order.jar  spring-docker-order.jar
ENTRYPOINT ["java" ,"-jar","/spring-docker-order.jar"]