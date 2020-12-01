#!/usr/bin/env bash

#java -javaagent:./nsf-javaagent-home/nsf-agent-1.0-SNAPSHOT-jar-with-dependencies.jar=d -jar target/simple-consumer-1.0-SNAPSHOT.jar


if [ "in-nsf" = "$1" ]; then
    java -javaagent:./nsf-javaagent-home/nsf-agent-premain-1.0.jar=debug -jar target/nsf-demo-stock-provider-1.0-SNAPSHOT.jar
else
    java -jar target/nsf-demo-stock-provider-1.0-SNAPSHOT.jar
fi;