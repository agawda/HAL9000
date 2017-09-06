#!/usr/bin/env bash
echo "===================== Running maven build ====================="
set -e
cd HAL9000
mvn package
echo "Maven completed"
echo "Runinng spring app"
cd springapp/target
gnome-terminal -e "java -jar springapp-0.0.1-SNAPSHOT.jar"
echo "Sleeping for 15s..."
sleep 15
echo "Running crawler app"
cd ../../..
ls
gnome-terminal -e "sh scheduledrun.sh"
