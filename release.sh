#!/bin/sh
# https://stackoverflow.com/a/41315915/330457
mvn clean -DskipTests -Darguments=-DskipTests release:prepare release:perform