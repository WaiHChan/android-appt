#!/bin/bash

source checkForJava.sh

if ! checkForJava; then
    echo "** Java is not configured correctly in your environment"
    exit 1
fi

if [ $# -lt 1 ]; then
  echo "** Missing your student id"
  exit 1
fi


studentId=$1

./mvnw --batch-mode archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeGroupId=io.github.davidwhitlock.cs410J \
  -DarchetypeArtifactId=student-archetype \
  -DarchetypeVersion=2021.3.0 \
  -DgroupId=edu.pdx.cs410J.${studentId} \
  -DartifactId=student \
  -Dpackage=edu.pdx.cs410J.${studentId} \
  -Dversion=2021.0.0
