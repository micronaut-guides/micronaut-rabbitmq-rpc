FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/bookcatalogue
WORKDIR /home/app/bookcatalogue

RUN native-image --no-server -cp build/libs/bookcatalogue-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/bookcatalogue /app/bookcatalogue
ENTRYPOINT ["/app/bookcatalogue", "-Xmx68m"]
