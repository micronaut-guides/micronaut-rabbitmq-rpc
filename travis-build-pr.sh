#!/bin/bash
set -e

export EXIT_STATUS=0

echo "Executing tests for branch $TRAVIS_BRANCH"
./gradlew --console=plain clean

./gradlew --console=plain complete:bookcatalogue:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  exit $EXIT_STATUS
fi

./gradlew --console=plain complete:bookinventory:check || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  exit $EXIT_STATUS
fi

./gradlew --console=plain complete:bookrecommendation:check || EXIT_STATUS=$?

exit $EXIT_STATUS
