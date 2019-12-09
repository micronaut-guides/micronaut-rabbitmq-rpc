FROM oracle/graalvm-ce:19.3.0-java8 as graalvm
COPY . /home/app/bookinventory
WORKDIR /home/app/bookinventory
RUN gu install native-image
RUN native-image --no-server -cp build/libs/bookinventory-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/bookinventory /app/bookinventory
ENTRYPOINT ["/app/bookinventory","-Xmx68m"]