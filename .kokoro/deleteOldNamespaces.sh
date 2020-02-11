#!/bin/bash

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