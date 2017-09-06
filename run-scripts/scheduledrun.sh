cd HAL9000/crawler/target/
FILE=crawler-0.0.1-SNAPSHOT.jar
while true; do
if [ -e "$FILE" ]
then
    	echo "File " ${FILE} " exists, executing"
	now=$(date +"%T")
	echo "Current time : $now"
	gnome-terminal -e "java -jar $FILE"
else
    	echo "File does not exist"
	return 0;
fi
	echo "Sleeping for 3 h"
   	sleep 3h
done
