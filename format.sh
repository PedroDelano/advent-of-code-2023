#!/bin/bash

JAVA_FORMATTER=google-java-format-1.19.2-all-deps.jar

# Loop through all files in the current directory and checks if it's a .java file
# it's necessary to loop through all subdirectories as well
for file in $(find . -name "*.java"); do
	# Format the file
	echo "Formatting $file..."
	java -jar $JAVA_FORMATTER --replace $file
done
