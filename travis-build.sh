#!/bin/bash
set -e

export EXIT_STATUS=0

./gradlew --console=plain complete/bookcatalogue:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  exit $EXIT_STATUS
fi

./gradlew --console=plain  complete/bookinventory:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  exit $EXIT_STATUS
fi

./gradlew --console=plain complete/bookrecommendation:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  exit $EXIT_STATUS
fi

echo "Starting services"
# Running the services manually. One of them doesn't start when using "-parallel"
./gradlew --console=plain complete/bookrecommendation:run &
./gradlew --console=plain complete/bookcatalogue:run &
./gradlew --console=plain complete/bookinventory:run &

echo "Waiting 60 seconds for microservices to start"
sleep 60

cd complete

./gradlew acceptance:test --rerun-tasks --console=plain || EXIT_STATUS=$?

killall -9 java

if [ $EXIT_STATUS -ne 0 ]; then
  exit $EXIT_STATUS
fi

cd ..

curl -O https://raw.githubusercontent.com/micronaut-projects/micronaut-guides/master/travis/build-guide
chmod 777 build-guide

./build-guide || EXIT_STATUS=$?

curl -O https://raw.githubusercontent.com/micronaut-projects/micronaut-guides/master/travis/republish-guides-website.sh
chmod 777 republish-guides-website.sh

./republish-guides-website.sh || EXIT_STATUS=$?

exit $EXIT_STATUS
