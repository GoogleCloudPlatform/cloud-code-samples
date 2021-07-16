#!/bin/bash -eu
#
# Copyright 2019 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

PORT=${PORT:-8080}
url=${1:-'http://localhost:'$PORT}
token=${2:-''}
expected='you successfully deployed a container image to Cloud Run'
retries=10
interval=5

for i in $(seq 0 $retries); do

    html="$(curl -si $url -H "Authorization: Bearer $token")" || html=""

    if echo "$html" | grep -q "$expected"
    then
        echo "Expected content found -- site is up"
        echo "END CONTENT TEST: Success! ✅"
        exit 0
    else
        echo "Expected content not found. retrying in $interval sec..."
        sleep $interval
    fi
done

echo "Error! Expected content not found."
echo "Was looking for '$expected'; not found in:"
echo "$html"
echo "END CONTENT TEST: Fail! 💩"

exit 1
