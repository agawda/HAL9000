# Robot ![biuld](https://travis-ci.org/agawda/HAL9000.svg?branch=master)
Robot collects book information for gratis and promo books.

### Authors

 - Michał
 - Ania
 - French Ninja Architect: Arek 
 
## Running

- goto target directory 
- run from console: 

```sh
$ mvn clean install
$ java -jar robot-0.0.1-SNAPSHOT.jar
```

Site (TODO)

``mvn site``

Jacoco (TODO)

``mvn jacoco:report``

Sonar (TODO)

``mvn sonar:sonar``

Checkstyle (TODO)

``mvn checkstyle:checkstyle``

## What project does:
- Robot collects book information for gratis and promo books
- Book information collected at minimum is: title, genre, author(s), promo details, price
- If book has subtitle, it's collected as well
- Web-UI
- Book information is collected from 3 DIFFERENT libraries (screen scraping or REST API)
- Robot runs once per day, results are appended
- Run results are persisted in DB
- Runs are logged along with their statistics 
- Project mantra

## How to run project:
The project contain scripts that can be executed to run the applications. The command that needs to be executed is 
`sh mainScript.sh` and should be executed from the directory above the HAL9000 directory. 

 The application it runs are: the spring application which communicates with the db and works in Tomcat container at
  port 8080. The other app is executed every 24 hours and scraps the results from following bookstores:
- Gandalf
- Matras
- Bonito
- GoogleBooks

The scripts contain also script for cloning and switching to master branch `cloning.sh` but it is not enabled by default 
in the main script (with assumption that the user already cloned the repository to get those scripts)

Before running the scripts, the user has to have [PostgreSQL](https://www.postgresql.org/download/) installed and already
 created a database named `test_db`, running on port 5432.
The script will ask for the password to the database with user `postgres` and will ask if the user wants to create schemas 
for the application from scratch (If pressed `y` and the database already has any records, those will be deleted).

The other tools required to be installed in order for the app to run correctly:
- [java](https://www.java.com/en/download/help/download_options.xml), preferably the newest version
- [maven](https://maven.apache.org/install.html)
- [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- any modern web [browser](https://en.wikipedia.org/wiki/List_of_web_browsers)

The scrapper app will create logs every time it runs inside the logs directory. Log file will have a time of it's creation 
attached to it's name.



## Demo.md:
https://github.com/agawda/HAL9000/wiki

## Kanban tables:
https://waffle.io/agawda/HAL9000