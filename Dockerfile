FROM maven:3.5.4-jdk-8-alpine AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package


FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /usr/src/app/target/KafkaDemoTwitterProducer.jar /usr/app/app.jar  
ENTRYPOINT ["java","-jar","-Dkafka.endpoints=${KAFKA_BOOTSTRAP_ENDPOINTS}","-Dkafka.keystore.location=${KAFKA_KEYSTORE_LOCATION}","-Dkafka.keystore.password=${KAFKA_KEYSTORE_PASSWORD}","-Dtwitter.api.key=${TWITTER_API_KEY}","-Dtwitter.api.secret=${TWITTER_API_SECRET}","-Dtwitter.access.token=${TWITTER_ACCESS_TOKEN}","-Dtwitter.access.secret=${TWITTER_ACCESS_SECRET}","/usr/app/app.jar"]  
EXPOSE 8895





