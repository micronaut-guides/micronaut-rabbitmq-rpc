FROM oracle/graalvm-ce:19.3.0-java8 as graalvm
COPY . /home/app/bookrecommendation
WORKDIR /home/app/bookrecommendation
RUN gu install native-image
RUN native-image --no-server -cp build/libs/bookrecommendation-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/bookrecommendation /app/bookrecommendation
ENTRYPOINT ["/app/bookrecommendation","-Xmx68m"]