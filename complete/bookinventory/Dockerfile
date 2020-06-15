FROM oracle/graalvm-ce:20.1.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/bookinventory
WORKDIR /home/app/bookinventory

RUN native-image --no-server -cp build/libs/bookinventory-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/bookinventory/bookinventory /app/bookinventory
ENTRYPOINT ["/app/bookinventory"]
