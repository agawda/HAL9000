echo "Checking if the key file exists"
FILE=`find HAL9000/crawler/src/main/resources/ -name "key.txt"`
if [ -e "$FILE" ]
then
    	echo "File exists: " ${FILE}
	return 0;
else
    	echo "File does not exist, creating"
	echo "GoogleKey=&key=AIzaSyA9pzKTyLsStKstnmN_Rgr6UUK-2IYkmf4" > HAL9000/crawler/src/main/resources/key.txt
fi
