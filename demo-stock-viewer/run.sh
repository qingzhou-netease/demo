#!/usr/bin/env bash

if [ "in-nsf" = "$1" ]; then
    java -javaagent:./nsf-javaagent-home/nsf-agent-premain-1.0.jar=debug -jar target/nsf-demo-stock-viewer-1.0-SNAPSHOT.jar
else
    java -jar target/nsf-demo-stock-viewer-1.0-SNAPSHOT.jar
fi;