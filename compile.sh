#!/bin/bash

SRC_DIR="src"
OUT_DIR="out"
JAR_NAME="project.jar"

JAVA_FILES=$(find $SRC_DIR -name "*.java")

mkdir -p $OUT_DIR

echo "Compiling Java files..."
javac -d $OUT_DIR $JAVA_FILES

if [ $? -eq 0 ]; then
    echo "Compilation successful."
else
    echo "Compilation failed."
    exit 1
fi

MANIFEST_FILE="manifest.txt"
echo "Manifest-Version: 1.0" > $MANIFEST_FILE
echo "Main-Class: main.Main" >> $MANIFEST_FILE

echo "Creating .jar file..."
jar cvfm $JAR_NAME $MANIFEST_FILE -C $OUT_DIR .

if [ $? -eq 0 ]; then
    echo ".jar file created successfully: $JAR_NAME"
else
    echo "Failed to create .jar file."
    exit 1
fi
