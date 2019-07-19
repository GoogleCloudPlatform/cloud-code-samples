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

# retries, interval, url may be passed as arguments
# retries: -r
# interval (in seconds): -i
# url: -u

## Defaults
retries=10
interval=5
url="http://localhost:3000"

while getopts r:i:u: option
do
    case "${option}"
    in
        r) retries=$OPTARG;;
        i) interval=$OPTARG;;
        u) url=$OPTARG;
    esac
done

echo "START CONNECTION TEST"
echo "retries: "$retries
echo "interval: "$interval
echo "url: "$url

for i in $(seq 0 $retries); do
    
    status="$(curl -sL -w "%{http_code}" -I "$url" -o /dev/null)" || status='000'
    
    if [[ $status == '200' ]]
    then
        echo "END CONNECTION TEST: Success"
        exit 0
    else
        if [ $i -gt 0 ]; then
            echo "unable to connect. retrying in $interval sec..."
            sleep $interval
        fi
    fi
done

echo "Error! Unable to connect"
echo "Status code received: $status"
echo "END CONNECTION TEST: Fail!"

exit 1
