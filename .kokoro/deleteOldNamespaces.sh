#!/bin/bash
# Copyright 2023 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


now=$(date +%s)
for nm in $(kubectl get namespaces --field-selector metadata.name!=default,metadata.name!=kube-public,metadata.name!=kube-system -o name)
do
  echo "Working on $nm now."
  #date -d  "2020-01-25T05:50:57Z" +"%s"
  time=$(kubectl get "$nm" -o=jsonpath='{.metadata.creationTimestamp}')
  createdAt=$(date -d $time +"%s")

  elapsed=$now-$createdAt
  echo "namespace created ($now-$createdAt) ago"

  # compare to two days time span in seconds
  if [172800<$elapsed]; then
    echo "removing $nm"
    kubectl delete $nm
  else
    echo "$nm stays as it's less than 2 days old" 
  fi
done