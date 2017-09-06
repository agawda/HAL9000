#!/usr/bin/env bash
echo -n "Input a database password\n\b" 
read PASSWORD

#prompt for creating schemas
CREATE_DB=false
read -r -p "Do you want to create DB schemas? [y/N] " RESPONSE
case "$RESPONSE" in
    [yY][eE][sS]|[yY])
        CREATE_DB=true
    echo "Will create schemas then!"
        ;;
    *)
        echo "Without schemas then!"
        ;;
esac
############################

echo "Applying password"
FILE=`find HAL9000/springapp/src/main/ -name "application.properties"`
DB_PROPERTY='spring.datasource.password='
FULL_LINE=${DB_PROPERTY}${PASSWORD}
echo "Line to insert = " ${FULL_LINE}
if [ -e "$FILE" ]
then
    	echo "File exists: " ${FILE}
else
    	echo "File does not exist"
	return 0;
fi
echo "Line to change = " ${DB_PROPERTY}
if grep -q ${DB_PROPERTY} ${FILE};
then
   	echo "There is required string inside: " ${DB_PROPERTY};
	RESULT=`grep ${DB_PROPERTY} ${FILE} | cut -d "=" -f2`
	echo "Found password: " ${RESULT};
		echo "Replacing a password..."
		echo "" | sed -i '/'${DB_PROPERTY}'/c\'${FULL_LINE} ${FILE}
		AFTER_CUT_RESULT=`grep ${DB_PROPERTY} ${FILE} | cut -d "=" -f2`
		echo "\nChecking if the adding was successful"
		if  [ "$AFTER_CUT_RESULT" = "" ];
		then
			echo "Could not add a password"
		else 
			echo "Added the password " ${PASSWORD}
		fi
else 
	echo "No string inside";
fi
DB_CREATION_PROPERTY_TRUE='spring.jpa.hibernate.ddl-auto=create'
DB_CREATION_PROPERTY_FALSE='#spring.jpa.hibernate.ddl-auto=create'
if ${CREATE_DB};
then
    echo "Setting the schema creation to true"
        if grep -q ${DB_CREATION_PROPERTY_FALSE} ${FILE};
        then
            echo "" | sed -i '/'${DB_CREATION_PROPERTY_FALSE}'/c\'${DB_CREATION_PROPERTY_TRUE} ${FILE}
        fi
    else
    echo "Setting the schema creation to false"
        if grep -q ${DB_CREATION_PROPERTY_TRUE} ${FILE};
        then
            echo "" | sed -i '/'${DB_CREATION_PROPERTY_TRUE}'/c\'${DB_CREATION_PROPERTY_FALSE} ${FILE}
        fi
fi
