#!/bin/bash

JAR_NAME="project.jar"

if [ ! -f $JAR_NAME ]; then
    echo "$JAR_NAME does not exist. Please compile the project first."
    exit 1
fi

echo "Running $JAR_NAME..."
java -cp "$JAR_NAME" Main
