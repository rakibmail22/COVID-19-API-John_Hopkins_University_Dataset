#!/bin/bash

java -jar -Xms200M -Xmx300M -Dspring.profiles.active=dev build/libs/covid-tracker-rest-0.0.1-SNAPSHOT.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=1044
#java -jar -Xms200M -Xmx300M covid-tracker-rest-0.0.1-SNAPSHOT.jar