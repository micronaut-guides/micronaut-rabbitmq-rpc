FROM oracle/graalvm-ce:19.3.0-java8 as graalvm
COPY . /home/app/bookcatalogue
WORKDIR /home/app/bookcatalogue
RUN gu install native-image
RUN native-image --no-server -cp build/libs/bookcatalogue-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/bookcatalogue /app/bookcatalogue
ENTRYPOINT ["/app/bookcatalogue","-Xmx68m"]