FROM openjdk:8-jdk-alpine 
RUN apk --no-cache add curl
COPY build/libs/*.jar bookrecommendation.jar
CMD java ${JAVA_OPTS} -jar bookrecommendation.jar