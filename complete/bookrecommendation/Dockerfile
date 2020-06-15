FROM oracle/graalvm-ce:20.1.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/bookrecommendation
WORKDIR /home/app/bookrecommendation

RUN native-image --no-server -cp build/libs/bookrecommendation-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/bookrecommendation/bookrecommendation /app/bookrecommendation
ENTRYPOINT ["/app/bookrecommendation"]
