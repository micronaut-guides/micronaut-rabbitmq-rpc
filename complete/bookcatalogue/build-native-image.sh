#!/bin/bash

./gradlew assemble

#java -cp build/libs/bookcatalogue-0.1.jar io.micronaut.graal.reflect.GraalClassLoadingAnalyzer

native-image --no-server \
             --class-path build/libs/bookcatalogue-0.1.jar \
             -H:ReflectionConfigurationFiles=reflect.json \
             -H:EnableURLProtocols=http \
             -H:IncludeResources="logback.xml|application.yml" \
             -H:Name=bookcatalogue \
             -H:Class=example.micronaut.bookcatalogue.Application \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:+AllowVMInspection \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=com.sun.jndi.dns.DnsClient